package util;

import account.PayAccount;
import annotation.Sign;
import entity.AbstractEntity;

import java.lang.reflect.Field;

/**
 * description: 签名工具类
 * https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=3
 *
 * @author: zmj
 * @create: 2018/1/10
 */
public class SignUtil {

    public static final String SIGN_TYPE = "MD5";

    /**
     * 生成签名
     * @param payAccount 商户信息
     * @param entity
     * @throws IllegalAccessException
     */
    public static void makeSign(PayAccount payAccount, AbstractEntity entity) throws IllegalAccessException {
        preMakeSign(entity);
        String toSignStr = makePreSignStr(payAccount, entity);
        String signStr = makeSignStr(toSignStr);
        afterMakeSign(entity, signStr);
    }

    /**
     * 签名前预处理，签名属性不参与签名
     * @param entity
     * @throws IllegalAccessException
     */
    private static void preMakeSign(AbstractEntity entity) throws IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        for(Field field : fields) {
            Sign sign = field.getAnnotation(Sign.class);
            if(sign == null) continue;
            field.setAccessible(true);
            if(sign.type().equals(SignType.SIGN_STR)) {
                field.set(entity, null);
                continue;
            }
            if(sign.type().equals(SignType.SIGN_TYPE)) {
                field.set(entity, SIGN_TYPE);
                continue;
            }
        }
    }

    /**
     * 组装需加密的串
     * @param payAccount
     * @param entity
     * @return
     */
    private static String makePreSignStr(PayAccount payAccount, AbstractEntity entity) {
        StringBuilder stringBuilder = new StringBuilder(ParseUtil.toUrlString(entity));
        stringBuilder = stringBuilder.delete(0, 1);
        stringBuilder.append("&key=").append(payAccount.getApi_password());

        return stringBuilder.toString();
    }

    /**
     * 正式签名，采用的算法需与SIGN_TYPE一致
     * @param preSignStr 需加密的串，具体规则详见api
     * @return
     */
    private static String makeSignStr(String preSignStr) {
        return MD5Util.getMD5(preSignStr).toString();
    }

    /**
     * 将签名赋值
     * @param entity
     * @param signStr
     * @throws IllegalAccessException
     */
    private static void afterMakeSign(AbstractEntity entity, String signStr) throws IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        for(Field field : fields) {
            Sign sign = field.getAnnotation(Sign.class);
            if(sign == null) continue;
            if(sign.type().equals(SignType.SIGN_STR)) {
                field.setAccessible(true);
                field.set(entity, signStr);
                break;
            }
        }
    }

    /**
     * 验证签名
     * @param payAccount
     * @param entity
     * @return
     */
    public static boolean validSign(PayAccount payAccount, AbstractEntity entity) throws IllegalAccessException {
        String signStr = getSginStr(entity);//待验证签名
        if(signStr == null) return false;
        makeSign(payAccount, entity);
        String signStr_New = getSginStr(entity);//生成的签名

        return signStr.equals(signStr_New);
    }

    /**
     * 获取签名值
     * @param entity
     * @return
     * @throws IllegalAccessException
     */
    private static String getSginStr(AbstractEntity entity) throws IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        for(Field field : fields) {
            Sign sign = field.getAnnotation(Sign.class);
            if(sign == null) continue;
            if(sign.type().equals(SignType.SIGN_STR)) {
                field.setAccessible(true);
                return String.valueOf(field.get(entity));
            }
        }

        return null;
    }
}
