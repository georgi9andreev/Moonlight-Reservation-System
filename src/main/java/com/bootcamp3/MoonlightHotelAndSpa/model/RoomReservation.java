package com.bootcamp3.MoonlightHotelAndSpa.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "room_reservations")
public class RoomReservation {

    @Id
    private Long id;

    private Instant createdAt;
    private Instant checkIn;
    private Instant checkOut;
    private int guests;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private double totalPrice;
    private String bedType;

    @OneToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    public RoomReservation() {
    }

    public RoomReservation(Long id, Instant createdAt, Instant checkIn, Instant checkOut, int guests,
                           User user, double totalPrice, String bedType, Room room) {
        this.id = id;
        this.createdAt = createdAt;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.user = user;
        this.totalPrice = totalPrice;
        this.bedType = bedType;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Instant checkIn) {
        this.checkIn = checkIn;
    }

    public Instant getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Instant checkOut) {
        this.checkOut = checkOut;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
