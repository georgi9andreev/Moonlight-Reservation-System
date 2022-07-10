package com.bootcamp3.MoonlightHotelAndSpa.dto.room;

import com.bootcamp3.MoonlightHotelAndSpa.enumeration.RoomType;

import java.util.ArrayList;

public class RoomDto {

    private RoomType title;
    private String image;
    private ArrayList<String> images;
    private String description;
    private Integer area;
    private Integer people;
    private Double price;

    public RoomDto(RoomType title, String image, ArrayList<String> images, String description,
                   Integer area, Integer people, Double price) {
        this.title = title;
        this.image = image;
        this.images = images;
        this.description = description;
        this.area = area;
        this.people = people;
        this.price = price;
    }

    public RoomType getTitle() {
        return title;
    }

    public void setTitle(RoomType title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
