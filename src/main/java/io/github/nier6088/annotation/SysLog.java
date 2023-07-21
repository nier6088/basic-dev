package io.github.nier6088.annotation;

import java.lang.annotation.*;

/**
 * @projectName: miquan-saas
 * @package: com.boolib.simple.common.annotation
 * @className: SysLog
 * @author: yangjun
 * @description:
 * @date: 2023/5/9 11:25
 * @version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
