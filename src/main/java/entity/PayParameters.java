package entity;

import annotation.Alias;
import annotation.Sign;
import util.DateUtil;
import util.RandomUtil;
import util.SignType;

/**
 * description:
 *
 * @author: zmj
 * @create: 2018/1/10
 */
public class PayParameters extends AbstractEntity {
    private String timeStamp;
    private String nonceStr;
    @Alias(value = "package")
    private String packageStr;
    @Sign(type = SignType.SIGN_TYPE)
    private String signType;
    @Sign
    private String paySign;

    public PayParameters() {
        this.setTimeStamp(DateUtil.getCurrentTime());
        this.setNonceStr(RandomUtil.getRandomString());
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
}
