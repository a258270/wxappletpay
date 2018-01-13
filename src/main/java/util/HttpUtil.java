package util;

import entity.AbstractEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * description:
 *
 * @author: zmj
 * @create: 2018/1/10
 */
public class HttpUtil {

    private static final String HTTP_METHOD_POST = "POST";
    private static final String HTTP_METHOD_GET = "GET";
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, AbstractEntity entity) {
        String result = "";
        ;
        try {
            String urlNameString = url + ParseUtil.toUrlString(entity);

            HttpURLConnection connection = createConnection(urlNameString, HTTP_METHOD_GET);

            result = getResponse(connection, HTTP_METHOD_GET, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, AbstractEntity entity) {

        String result = "";
        try {
            HttpURLConnection conn = createConnection(url, HTTP_METHOD_POST);

            result = getResponse(conn, HTTP_METHOD_POST, entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static HttpURLConnection createConnection(String url, String method_type) throws IOException {
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setRequestProperty("charset", "UTF-8");

        connection.setRequestMethod(method_type);

        if (connection.getRequestMethod().equals(method_type)) {
            connection.setRequestProperty("Content-Type", "text/xml");

            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
        }

        return connection;
    }

    private static String getResponse(HttpURLConnection connection, String method_type, AbstractEntity entity) throws IOException {
        // 建立实际的连接
        connection.connect();

        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        if(connection.getRequestMethod().equals(method_type)) {
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(ParseUtil.toXmlString(entity));
            // flush输出流的缓冲
            out.flush();
        }
        try{
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        }
        finally {
            if(out!=null){
                out.close();
            }
            if(in!=null){
                in.close();
            }
        }

        return result.toString();
    }
}
