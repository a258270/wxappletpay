package util;

import java.util.Random;

/**
 * description:
 *
 * @author: zmj
 * @create: 2018/1/10
 */
public class RandomUtil {

    public static String getRandomString() {
        StringBuilder stringBuilder = new StringBuilder();
        Random r = new Random();

        for(int i = 0; i < 30; i++) {
            stringBuilder.append(r.nextInt(10));
        }

        return stringBuilder.toString();
    }
}
