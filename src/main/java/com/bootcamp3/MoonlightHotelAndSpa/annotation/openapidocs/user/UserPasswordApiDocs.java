package com.bootcamp3.MoonlightHotelAndSpa.annotation.openapidocs.user;

import com.bootcamp3.MoonlightHotelAndSpa.annotation.util.CustomResponseCode;
import com.bootcamp3.MoonlightHotelAndSpa.model.errormessage.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(summary = "")
@CustomResponseCode(responseCode = "204", description = "No Content", content = @Content())
@CustomResponseCode(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserPasswordApiDocs {
    String summary() default "";
}
