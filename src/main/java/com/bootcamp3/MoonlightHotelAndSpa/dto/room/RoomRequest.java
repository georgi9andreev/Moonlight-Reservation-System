package com.bootcamp3.MoonlightHotelAndSpa.dto.room;

import com.bootcamp3.MoonlightHotelAndSpa.enumeration.RoomType;

import java.util.ArrayList;

public class RoomRequest extends RoomDto{

    private Integer count;

    public RoomRequest(RoomType title, String image, ArrayList<String> images, String description,
                       Integer area, Integer people, Double price, Integer count) {
        super(title, image, images, description, area, people, price);
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
