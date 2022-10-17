package com.bootcamp3.MoonlightHotelAndSpa.annotation.openapidocs.transfer;

import com.bootcamp3.MoonlightHotelAndSpa.annotation.util.CustomResponseCode;
import com.bootcamp3.MoonlightHotelAndSpa.dto.transfer.CarCategoryResponse;
import com.bootcamp3.MoonlightHotelAndSpa.model.errormessage.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(summary = "Create a new car category")
@CustomResponseCode(responseCode = "201", description = "Successful Operation", content = @Content( schema = @Schema(implementation = CarCategoryResponse.class)))
@CustomResponseCode(responseCode = "400", description = "BadRequest", content = @Content( schema = @Schema(implementation = ErrorResponse.class)))
@CustomResponseCode(responseCode = "401", description = "Unauthorized")
@CustomResponseCode(responseCode = "403", description = "Forbidden")
@SecurityRequirement(name = "bearerAuth")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateCarCategoryApiDocs {
}
