package com.bootcamp3.MoonlightHotelAndSpa.dto.room;

import com.bootcamp3.MoonlightHotelAndSpa.enumeration.RoomType;
import com.bootcamp3.MoonlightHotelAndSpa.enumeration.RoomView;

import java.util.ArrayList;

public class RoomRequest extends RoomDto{

    private Integer count;

    public RoomRequest(RoomType title, String image, ArrayList<String> images, String description, RoomView roomView,
                       Integer area, Integer people, Double price, Integer count) {
        super(title, image, images, description, roomView, area, people, price);
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
