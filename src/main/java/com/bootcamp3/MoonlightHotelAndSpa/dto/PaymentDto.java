package com.bootcamp3.MoonlightHotelAndSpa.dto;

import lombok.Builder;

@Builder
public class PaymentDto {

    private Long id;

    private String description;

    private String itemDescription;

    private String classTypeId;

    private Double totalAmount;

    private String capturePath;

    public PaymentDto() {
    }

    public PaymentDto(Long id, String description, String itemDescription, String classTypeId, Double totalAmount, String capturePath) {
        this.id = id;
        this.description = description;
        this.itemDescription = itemDescription;
        this.classTypeId = classTypeId;
        this.totalAmount = totalAmount;
        this.capturePath = capturePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getClassTypeId() {
        return classTypeId;
    }

    public void setClassTypeId(String classTypeId) {
        this.classTypeId = classTypeId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCapturePath() {
        return capturePath;
    }

    public void setCapturePath(String capturePath) {
        this.capturePath = capturePath;
    }
}
