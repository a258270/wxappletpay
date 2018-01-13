import account.WxAppletAccount;
import api.PayApi;
import entity.PayParameters;
import entity.Unifiedorder;

/**
 * description:
 *
 * @author: zmj
 * @create: 2018/1/9
 */
public class WxAppletPayProxy {

    private WxAppletAccount wxAppletAccount;

    public WxAppletAccount getWxAppletAccount() {
        return wxAppletAccount;
    }

    public void setWxAppletAccount(WxAppletAccount wxAppletAccount) {
        this.wxAppletAccount = wxAppletAccount;
    }

    private PayApi payApi;

    public PayParameters getPayParameters(Unifiedorder unifiedorder) throws Exception {
        unifiedorder.setPayInfoByAccount(this.wxAppletAccount);

        return payApi.getPayParameters(this.wxAppletAccount.getPayAccount(), unifiedorder);
    }


}
