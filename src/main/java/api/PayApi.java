package api;

import account.PayAccount;
import entity.*;
import util.Const;
import util.HttpUtil;
import util.ParseUtil;
import util.SignUtil;

/**
 * description:
 *
 * @author: zmj
 * @create: 2018/1/10
 */
public class PayApi {
    public PayParameters getPayParameters(PayAccount payAccount, Unifiedorder unifiedorder) throws Exception {
        //统一下单参数签名
        SignUtil.makeSign(payAccount, unifiedorder);

        //获取预付单信息
        String responseXml = HttpUtil.sendPost(Const.URL_UNIFIEDORDER, unifiedorder);

        //解析预付单信息
        PrePay prePay = ParseUtil.parseEntityFromXml(responseXml, PrePay.class);

        PayParameters payParameters = prePay.toPayParameters();

        //预付单信息再次签名获得五个参数
        SignUtil.makeSign(payAccount, payParameters);

        return payParameters;
    }

    public Order orderQueryByTransactionId(OrderQuery orderQuery) {
        return null;
    }

}
