package io.github.nier6088.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClickLimit {
    String message() default "";

    long expire() default 3L;
}
