package com.bootcamp3.MoonlightHotelAndSpa.annotation.util;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ErrorCodes {
    CustomResponseCode[] value() default {};
}
