package com.bootcamp3.MoonlightHotelAndSpa.runner;

import com.bootcamp3.MoonlightHotelAndSpa.enumeration.RoomType;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.RoomView;
import com.bootcamp3.MoonlightHotelAndSpa.model.Room;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class RoomRunner implements CommandLineRunner {

    private final RoomService roomService;

    @Autowired
    public RoomRunner(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public void run(String... args) throws Exception {

        Room standardSeaViewRoom = new Room();
        standardSeaViewRoom.setTitle(RoomType.STANDARD);
        standardSeaViewRoom.setImage("some_picture.jpg");
        standardSeaViewRoom.setImages(new HashSet<>());
        standardSeaViewRoom.setDescription("Standard room with sea view");
        standardSeaViewRoom.setArea(24);
        standardSeaViewRoom.setRoomView(RoomView.SEA);
        standardSeaViewRoom.setPeople(2);
        standardSeaViewRoom.setPrice(220.0);
        standardSeaViewRoom.setCount(2);

        roomService.save(standardSeaViewRoom);

        Room standardSwimmingPoolViewRoom = new Room();
        standardSwimmingPoolViewRoom.setTitle(RoomType.STANDARD);
        standardSwimmingPoolViewRoom.setImage("some_picture.jpg");
        standardSwimmingPoolViewRoom.setImages(new HashSet<>());
        standardSwimmingPoolViewRoom.setDescription("Standard room with swimming pool view");
        standardSwimmingPoolViewRoom.setArea(24);
        standardSwimmingPoolViewRoom.setRoomView(RoomView.SWIMMING_POOL);
        standardSwimmingPoolViewRoom.setPeople(2);
        standardSwimmingPoolViewRoom.setPrice(220.0);
        standardSwimmingPoolViewRoom.setCount(2);

        roomService.save(standardSwimmingPoolViewRoom);

        Room standardGardenViewRoom = new Room();
        standardGardenViewRoom.setTitle(RoomType.STANDARD);
        standardGardenViewRoom.setImage("some_picture.jpg");
        standardGardenViewRoom.setImages(new HashSet<>());
        standardGardenViewRoom.setDescription("Standard room with garden view");
        standardGardenViewRoom.setArea(24);
        standardGardenViewRoom.setRoomView(RoomView.GARDEN);
        standardGardenViewRoom.setPeople(2);
        standardGardenViewRoom.setPrice(220.0);
        standardGardenViewRoom.setCount(4);

        roomService.save(standardGardenViewRoom);

        Room studioSeaViewRoom = new Room();
        studioSeaViewRoom.setTitle(RoomType.STUDIO);
        studioSeaViewRoom.setImage("some_picture.jpg");
        studioSeaViewRoom.setImages(new HashSet<>());
        studioSeaViewRoom.setDescription("Studio room with sea view");
        studioSeaViewRoom.setArea(34);
        studioSeaViewRoom.setRoomView(RoomView.SEA);
        studioSeaViewRoom.setPeople(3);
        studioSeaViewRoom.setPrice(320.0);
        studioSeaViewRoom.setCount(2);

        roomService.save(studioSeaViewRoom);

        Room studioSwimmingPoolViewRoom = new Room();
        studioSwimmingPoolViewRoom.setTitle(RoomType.STUDIO);
        studioSwimmingPoolViewRoom.setImage("some_picture.jpg");
        studioSwimmingPoolViewRoom.setImages(new HashSet<>());
        studioSwimmingPoolViewRoom.setDescription("Studio room with swimming pool view");
        studioSwimmingPoolViewRoom.setArea(34);
        studioSwimmingPoolViewRoom.setRoomView(RoomView.SWIMMING_POOL);
        studioSwimmingPoolViewRoom.setPeople(3);
        studioSwimmingPoolViewRoom.setPrice(320.0);
        studioSwimmingPoolViewRoom.setCount(2);

        roomService.save(studioSwimmingPoolViewRoom);

        Room studioGardenViewRoom = new Room();
        studioGardenViewRoom.setTitle(RoomType.STUDIO);
        studioGardenViewRoom.setImage("some_picture.jpg");
        studioGardenViewRoom.setImages(new HashSet<>());
        studioGardenViewRoom.setDescription("Studio room with garden view");
        studioGardenViewRoom.setArea(34);
        studioGardenViewRoom.setRoomView(RoomView.GARDEN);
        studioGardenViewRoom.setPeople(3);
        studioGardenViewRoom.setPrice(320.0);
        studioGardenViewRoom.setCount(2);

        roomService.save(studioGardenViewRoom);

        Room apartmentSeaView = new Room();
        apartmentSeaView.setTitle(RoomType.APARTMENT);
        apartmentSeaView.setImage("some_picture.jpg");
        apartmentSeaView.setImages(new HashSet<>());
        apartmentSeaView.setDescription("Apartment with sea view");
        apartmentSeaView.setArea(56);
        apartmentSeaView.setRoomView(RoomView.SEA);
        apartmentSeaView.setPeople(4);
        apartmentSeaView.setPrice(520.0);
        apartmentSeaView.setCount(2);

        roomService.save(apartmentSeaView);

        Room apartmentSwimmingPoolView = new Room();
        apartmentSwimmingPoolView.setTitle(RoomType.APARTMENT);
        apartmentSwimmingPoolView.setImage("some_picture.jpg");
        apartmentSwimmingPoolView.setImages(new HashSet<>());
        apartmentSwimmingPoolView.setDescription("Apartment with swimming pool view");
        apartmentSwimmingPoolView.setArea(56);
        apartmentSwimmingPoolView.setRoomView(RoomView.SWIMMING_POOL);
        apartmentSwimmingPoolView.setPeople(4);
        apartmentSwimmingPoolView.setPrice(520.0);
        apartmentSwimmingPoolView.setCount(1);

        roomService.save(apartmentSwimmingPoolView);
    }
}