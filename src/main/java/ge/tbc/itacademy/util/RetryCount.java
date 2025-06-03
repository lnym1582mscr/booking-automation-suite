package ge.tbc.itacademy.util;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RetryCount {
    int count() default 1;
}
