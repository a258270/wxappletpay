package entity;

import com.alibaba.fastjson.JSON;
import exception.ErrorException;
import org.junit.Test;
import util.ParseUtil;

import static org.junit.Assert.*;

/**
 * description:
 *
 * @author: zmj
 * @create: 2018/1/10
 */
public class UnifiedorderTest {

    @Test
    public void testUnifiedorder() throws ErrorException, IllegalAccessException, InstantiationException {
        Unifiedorder unifiedorder = new Unifiedorder();
        unifiedorder.setAppid("123");
        unifiedorder.setTotal_fee(500);

        String strJson = "<xml><appid>123</appid><total_fee>500</total_fee></xml>";
        System.out.println(ParseUtil.parseEntityFromXml(strJson, Unifiedorder.class));
    }
}