package com.bootcamp3.MoonlightHotelAndSpa.model;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String roomType;
    private String roomView;
    private Double price;

    @OneToOne(mappedBy = "room")
    private RoomReservation roomReservation;

    public Room() {
    }

    public Room(Long id, String roomType, String roomView, Double price, RoomReservation roomReservation) {
        this.id = id;
        this.roomType = roomType;
        this.roomView = roomView;
        this.price = price;
        this.roomReservation = roomReservation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomView() {
        return roomView;
    }

    public void setRoomView(String roomView) {
        this.roomView = roomView;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public RoomReservation getRoomReservation() {
        return roomReservation;
    }

    public void setRoomReservation(RoomReservation roomReservation) {
        this.roomReservation = roomReservation;
    }
}
