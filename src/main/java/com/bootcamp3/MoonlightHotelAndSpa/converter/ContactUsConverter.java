package com.bootcamp3.MoonlightHotelAndSpa.converter;

import com.bootcamp3.MoonlightHotelAndSpa.dto.ContactUsRequest;
import com.bootcamp3.MoonlightHotelAndSpa.model.ContactUs;

public class ContactUsConverter {

    public static ContactUs convertToContactUs(ContactUsRequest request) {

        ContactUs contact = new ContactUs();
        contact.setName(request.getName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setMessage(request.getMessage());

        return contact;
    }
}
