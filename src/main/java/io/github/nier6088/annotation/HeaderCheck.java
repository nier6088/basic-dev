package io.github.nier6088.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @projectName: miquan-saas
 * @package: com.boolib.simple.common.version
 * @className: ApiVersion
 * @author: yangjun
 * @description:
 * @date: 2023/5/9 15:55
 * @version: 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HeaderCheck {
    // 默认接口版本号1.0开始，这里我只做了两级，多级可在正则进行控制


    boolean checkUserToken() default true;
}
