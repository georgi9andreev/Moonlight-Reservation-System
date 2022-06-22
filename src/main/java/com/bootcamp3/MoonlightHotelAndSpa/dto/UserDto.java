package com.bootcamp3.MoonlightHotelAndSpa.dto;

import java.time.Instant;
import java.util.Set;

public class UserDto {

    private Long id;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private Set<String> roles;
    private Instant created;

    public UserDto() {
    }

    public UserDto(Long id, String email, String name, String surname, String phone, Set<String> roles, Instant created) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.roles = roles;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
