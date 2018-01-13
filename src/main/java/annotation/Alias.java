package annotation;

import java.lang.annotation.*;

/**
 * description: 属性别名注解,value值为别名
 *
 * @author: zmj
 * @create: 2018/1/10
 */
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Alias {
    String value() default "";
}
