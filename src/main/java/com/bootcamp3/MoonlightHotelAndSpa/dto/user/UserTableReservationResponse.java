package com.bootcamp3.MoonlightHotelAndSpa.dto.user;

import com.bootcamp3.MoonlightHotelAndSpa.dto.restaurant.TableResponse;

import java.time.Instant;

public class UserTableReservationResponse {

    private Long id;
    private Instant date;
    private int people;
    private double price;
    private TableResponse table;
    private UserResponse user;

    public UserTableReservationResponse() {
    }

    public UserTableReservationResponse(Long id, Instant date, int people, double price, TableResponse table, UserResponse user) {
        this.id = id;
        this.date = date;
        this.people = people;
        this.price = price;
        this.table = table;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TableResponse getTable() {
        return table;
    }

    public void setTable(TableResponse table) {
        this.table = table;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
