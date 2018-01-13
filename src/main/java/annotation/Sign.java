package annotation;

import util.Const;
import util.SignType;

import java.lang.annotation.*;

/**
 * description: 标记属性为签名所需属性
 * type为标记该属性具体用途
 * SIGN_STR：为存放签名值的属性
 * SIGN_TYPE：为存放签名类型的属性
 *
 * @author: zmj
 * @create: 2018/1/10
 */
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Sign {
    SignType type() default SignType.SIGN_STR;
}
