package entity;

import exception.ErrorException;
import org.junit.Test;
import util.ParseUtil;
import util.SignUtil;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * description:
 *
 * @author: zmj
 * @create: 2018/1/10
 */
public class PayParametersTest {

    @Test
    public void testAlias() throws ErrorException {
        PayParameters payParameters = new PayParameters();
        payParameters.setPackageStr("123");
        payParameters.setNonceStr("bbb");

        String strJson = ParseUtil.toJsonString(payParameters);

        PayParameters payParameters1 = ParseUtil.parseEntityFromJson(strJson, PayParameters.class);
    }

}