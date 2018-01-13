package util;

import annotation.Alias;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import entity.AbstractEntity;
import exception.ErrorException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author: zmj
 * @create: 2018/1/10
 */
public class ParseUtil {

    public static String toJsonString(AbstractEntity entity) {
        Map<String, Object> propertyMap =  entity.getPropertyMap();

        return JSON.toJSONString(propertyMap);
    }

    public static String toXmlString(AbstractEntity entity) {
        return appendXmlHeader(parsePropertiesToXmlString(entity));
    }

    private static String appendXmlHeader(String strXml) {
        StringBuilder stringBuilder = new StringBuilder("<xml>").append(strXml).append("</xml>");

        return stringBuilder.toString();
    }
    private static String parsePropertiesToXmlString(AbstractEntity entity) {
        Map<String, Object> propertyMap =  entity.getPropertyMap();

        StringBuilder stringBuilder = new StringBuilder();
        for(Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            String key = entry.getKey();
            stringBuilder.append("<");
            stringBuilder.append(key);
            stringBuilder.append(">");

            Object value = entry.getValue();
            stringBuilder.append("<![CDATA[");
            if(value instanceof AbstractEntity)
                stringBuilder.append(toJsonString((AbstractEntity) value));
            else
                stringBuilder.append(value);
            stringBuilder.append("]]>");

            stringBuilder.append("</");
            stringBuilder.append(entry.getKey());
            stringBuilder.append(">");
        }

        return stringBuilder.toString();
    }

    public static String toUrlString(AbstractEntity entity) {
        Map<String, Object> propertyMap =  entity.getPropertyMap();
        StringBuilder stringBuilder = new StringBuilder();
        for(Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            String key = entry.getKey();
            stringBuilder.append(key).append("=");
            Object value = entry.getValue();
            stringBuilder.append(value).append("&");
        }

        if(stringBuilder.length() > 0) {
            stringBuilder.insert(0, "?");
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        }

        return stringBuilder.toString();
    }

    public static <T extends AbstractEntity> T parseEntityFromXml(String strXml, Class<T> clazz) throws ErrorException {
        try {
            strXml = strXml.replace("\n", "");
            T entity = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            Document document = DocumentHelper.parseText(strXml);
            Element rootElement = document.getRootElement();
            List<Element> contents = rootElement.content();
            for (Element element : contents) {
                String key = element.getName();//返回结果的key
                String value = element.getStringValue();//返回结果的value
                for (Field field : fields) {
                    Alias alias = field.getAnnotation(Alias.class);
                    String fieldName = (alias != null && !alias.value().isEmpty()) ? alias.value() : field.getName();
                    if (fieldName.equals(key)) {
                        field.setAccessible(true);
                        if(field.getType().equals(String.class))
                            field.set(entity, value);
                        else
                            setField(entity, field, value);
                        break;
                    }
                }
            }
            return entity;
        }
        catch (Exception e) {
            throw new ErrorException("解析xml失败！", e);
        }
    }

    private static <T extends AbstractEntity> void setField( T entity, Field field, String value) throws IllegalAccessException {
        if(field.getType().equals(Integer.class)) {
            field.set(entity, Integer.valueOf(value));
        }
    }

    public static <T extends AbstractEntity> T parseEntityFromJson(String strJson, Class<T> clazz) throws ErrorException {
        try{
            T entity = parseObject(strJson, clazz);
            return entity;
        }
        catch (Exception e) {
            throw new ErrorException("解析json失败！");
        }
    }

    private static <T extends AbstractEntity> T parseObject(String strJson, Class<T> clazz) throws Exception {
        T entity = clazz.newInstance();
        JSONObject jsonObject = JSON.parseObject(strJson);
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            Alias alias = field.getAnnotation(Alias.class);
            String fieldName = (alias != null && !alias.value().isEmpty()) ? alias.value() : field.getName();
            field.setAccessible(true);

            field.set(entity, jsonObject.get(fieldName));
        }

        return entity;
    }
}
