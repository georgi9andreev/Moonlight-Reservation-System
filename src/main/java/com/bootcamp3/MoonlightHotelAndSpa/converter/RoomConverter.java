package com.bootcamp3.MoonlightHotelAndSpa.converter;

import com.bootcamp3.MoonlightHotelAndSpa.dto.room.RoomRequest;
import com.bootcamp3.MoonlightHotelAndSpa.dto.room.RoomResponse;
import com.bootcamp3.MoonlightHotelAndSpa.model.Image;
import com.bootcamp3.MoonlightHotelAndSpa.model.Room;

import java.util.Set;
import java.util.stream.Collectors;

public class RoomConverter {

    public static Room convertToRoom(RoomRequest roomRequest) {

        Room room = new Room();
        room.setTitle(roomRequest.getTitle());
        room.setImage(roomRequest.getImage());
        room.setImages(getImagesFromRequest(roomRequest));
        room.setDescription(roomRequest.getDescription());
        room.setRoomView(roomRequest.getRoomView());
        room.setArea(roomRequest.getArea());
        room.setPeople(roomRequest.getPeople());
        room.setPrice(roomRequest.getPrice());
        room.setCount(roomRequest.getCount());

        return room;
    }

    public static RoomResponse convertToRoomResponse (Room room) {

        return new RoomResponse.Builder()
                .addId(room.getId())
                .addTitle(room.getTitle())
                .addImage(room.getImage())
                .addImages(getImagesFromRooms(room))
                .addDescription(room.getDescription())
                .addRoomView(room.getRoomView())
                .addArea(room.getArea())
                .addPeople(room.getPeople())
                .addPrice(room.getPrice())
                .build();
    }

    public static Room update(Room room, RoomRequest roomRequest) {

        room.setTitle(roomRequest.getTitle());
        room.setImage(roomRequest.getImage());
        room.setImages(getImagesFromRequest(roomRequest));
        room.setDescription(roomRequest.getDescription());
        room.setRoomView(roomRequest.getRoomView());
        room.setArea(roomRequest.getArea());
        room.setPeople(roomRequest.getPeople());
        room.setPrice(roomRequest.getPrice());
        room.setCount(roomRequest.getCount());

        return room;
    }

    private static Set<String> getImagesFromRooms(Room room) {

        return room
                .getImages()
                .stream()
                .map(Image::getUrl)
                .collect(Collectors.toSet());
    }

    private static Set<Image> getImagesFromRequest(RoomRequest request) {

        return request
                .getImages()
                .stream()
                .map(image -> Image.builder().url(image).build())
                .collect(Collectors.toSet());
    }
}
