package entity;

import annotation.Alias;
import util.ParseUtil;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

/**
 * description:
 *
 * @author: zmj
 * @create: 2018/1/9
 */
public abstract class AbstractEntity {

    private final Map<String, Object> propertyMap = new TreeMap<String, Object>();

    protected void addProperty(String name, Object value) {
        this.propertyMap.put(name, value);
    }

    public Map<String, Object> getPropertyMap() {
        if(propertyMap.size() == 0 || !this.getPropertyCount().equals(propertyMap.size()))
            addProperties();

        return propertyMap;
    }

    protected void addProperties() {
        try{
            Field[] fields = this.getFields();
            for(Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(this);
                if(value != null) {
                    Alias alias = field.getAnnotation(Alias.class);
                    String fieldName = (alias != null && !alias.value().isEmpty()) ? alias.value() : field.getName();
                    this.addProperty(fieldName, value);
                }
            }
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Field[] getFields() {
        Class clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();

        return fields;
    }

    protected Integer getPropertyCount() {
        int iCount = 0;
        try{
            Field[] fields = this.getFields();
            for(Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(this);
                if(value != null) {
                    iCount++;
                }
            }
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return Integer.valueOf(iCount);
    }

    @Override
    public String toString() {
        return ParseUtil.toJsonString(this);
    }
}
