package com.bootcamp3.MoonlightHotelAndSpa.model.table;

import com.bootcamp3.MoonlightHotelAndSpa.enumeration.PaymentStatus;
import com.bootcamp3.MoonlightHotelAndSpa.model.User;

import javax.persistence.Table;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "table_reservations")
public class TableReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant date;

    private int people;

    private double price;

    private String updated;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private com.bootcamp3.MoonlightHotelAndSpa.model.table.Table table;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public TableReservation() {
    }

    public TableReservation(Long id, Instant date, int people, double price, String updated, PaymentStatus paymentStatus,
                            com.bootcamp3.MoonlightHotelAndSpa.model.table.Table table, User user) {
        this.id = id;
        this.date = date;
        this.people = people;
        this.price = price;
        this.updated = updated;
        this.paymentStatus = paymentStatus;
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

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public com.bootcamp3.MoonlightHotelAndSpa.model.table.Table getTable() {
        return table;
    }

    public void setTable(com.bootcamp3.MoonlightHotelAndSpa.model.table.Table table) {
        this.table = table;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
