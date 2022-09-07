package com.bootcamp3.MoonlightHotelAndSpa.annotation.openapidocs.room;

import com.bootcamp3.MoonlightHotelAndSpa.annotation.util.CustomResponseCode;
import com.bootcamp3.MoonlightHotelAndSpa.dto.RoomReservation.RoomReservationResponse;
import com.bootcamp3.MoonlightHotelAndSpa.model.errormessage.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(summary = "Create user room reservation")
@CustomResponseCode(responseCode = "200", description = "Successful Operation", content = @Content( schema = @Schema(implementation = RoomReservationResponse.class)))
@CustomResponseCode(responseCode = "400", description = "BadRequest", content = @Content( schema = @Schema(implementation = ErrorResponse.class)))
@CustomResponseCode(responseCode = "401", description = "Unauthorized")
@SecurityRequirement(name = "bearerAuth")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateRoomReservationApiDocs {
}
