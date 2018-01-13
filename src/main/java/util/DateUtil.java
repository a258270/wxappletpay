package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * description:
 *
 * @author: zmj
 * @create: 2018/1/10
 */
public class DateUtil {

    public static String getCurrentTime() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    public static String getDate(Date date) {
        if(date == null) date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }
}
