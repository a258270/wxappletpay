package account;

/**
 * description: 微信小程序账户
 *
 * @author: zmj
 * @create: 2018/1/9
 */
public class WxAppletAccount {

    private String appId;
    private String secret;
    private PayAccount payAccount;

    public WxAppletAccount() {
    }

    public WxAppletAccount(String appId, String secret, PayAccount payAccount) {
        this.appId = appId;
        this.secret = secret;
        this.payAccount = payAccount;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public PayAccount getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(PayAccount payAccount) {
        this.payAccount = payAccount;
    }
}
