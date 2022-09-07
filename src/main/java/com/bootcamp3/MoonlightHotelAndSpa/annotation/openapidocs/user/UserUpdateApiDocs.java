package com.bootcamp3.MoonlightHotelAndSpa.annotation.openapidocs.user;

import com.bootcamp3.MoonlightHotelAndSpa.annotation.util.CustomResponseCode;
import com.bootcamp3.MoonlightHotelAndSpa.dto.user.UserResponse;
import com.bootcamp3.MoonlightHotelAndSpa.model.errormessage.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(summary = "Update a User by ID")
@CustomResponseCode(responseCode = "200", description = "Successful Operation", content = @Content( schema = @Schema(implementation = UserResponse.class)))
@CustomResponseCode(responseCode = "400", description = "Bad Request", content = @Content( schema = @Schema(implementation = ErrorResponse.class)))
@CustomResponseCode(responseCode = "401", description = "Unauthorized")
@CustomResponseCode(responseCode = "403", description = "Forbidden")
@CustomResponseCode(responseCode = "404", description = "NotFound")
@SecurityRequirement(name = "bearerAuth")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserUpdateApiDocs {
}
