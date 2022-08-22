package com.bootcamp3.MoonlightHotelAndSpa.annotation.util;

import com.bootcamp3.MoonlightHotelAndSpa.model.errormessage.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@ApiResponse
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ErrorCodes.class)
public @interface CustomResponseCode {
    String responseCode() default "";

    String description() default "";

    Content[] content() default {@Content(schema = @Schema(implementation = ErrorMessage.class))};
}
