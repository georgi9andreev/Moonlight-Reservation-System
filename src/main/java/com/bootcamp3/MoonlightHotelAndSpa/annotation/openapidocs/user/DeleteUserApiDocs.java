package com.bootcamp3.MoonlightHotelAndSpa.annotation.openapidocs.user;

import com.bootcamp3.MoonlightHotelAndSpa.annotation.util.CustomResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(summary = "Remove a User by ID")
@CustomResponseCode(responseCode = "204", description = "No Content", content = @Content())
@CustomResponseCode(responseCode = "400", description = "BadRequest")
@CustomResponseCode(responseCode = "403", description = "Forbidden")
@CustomResponseCode(responseCode = "404", description = "Not Found")
@SecurityRequirement(name = "bearerAuth")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DeleteUserApiDocs {
}
