package com.bootcamp3.MoonlightHotelAndSpa.dto;

import com.bootcamp3.MoonlightHotelAndSpa.model.Role;
import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class UserDto {

    private Long Id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Set<Role> roles;
    private Date createdAt;
    private List<RoomReservation> reservations;

    public UserDto() {
    }

    public UserDto(Long id, String firstName, String lastName, String email, String phoneNumber,
                   Set<Role> roles, Date createdAt, List<RoomReservation> reservations) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
        this.createdAt = createdAt;
        this.reservations = reservations;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<RoomReservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<RoomReservation> reservations) {
        this.reservations = reservations;
    }
}
