package account;

/**
 * description: 商户账号
 *
 * @author: zmj
 * @create: 2018/1/9
 */
public class PayAccount {
    private String mch_id;
    private String mch_password;
    private String api_password;

    public PayAccount() {
    }

    public PayAccount(String mch_id, String mch_password, String api_password) {
        this.mch_id = mch_id;
        this.mch_password = mch_password;
        this.api_password = api_password;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getMch_password() {
        return mch_password;
    }

    public void setMch_password(String mch_password) {
        this.mch_password = mch_password;
    }

    public String getApi_password() {
        return api_password;
    }

    public void setApi_password(String api_password) {
        this.api_password = api_password;
    }
}
