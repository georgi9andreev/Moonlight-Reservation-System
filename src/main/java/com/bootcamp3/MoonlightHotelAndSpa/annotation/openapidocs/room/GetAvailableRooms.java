package com.bootcamp3.MoonlightHotelAndSpa.annotation.openapidocs.room;

import com.bootcamp3.MoonlightHotelAndSpa.annotation.util.CustomResponseCode;
import com.bootcamp3.MoonlightHotelAndSpa.dto.room.RoomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(summary = "Get available rooms")
@CustomResponseCode(responseCode = "200", description = "Successful Operation", content = @Content( array = @ArraySchema(schema = @Schema(implementation = RoomResponse.class))))
@CustomResponseCode(responseCode = "404", description = "Not Found")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GetAvailableRooms {
}
