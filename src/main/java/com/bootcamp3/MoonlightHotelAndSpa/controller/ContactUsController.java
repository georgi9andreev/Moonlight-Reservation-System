package com.bootcamp3.MoonlightHotelAndSpa.controller;

import com.bootcamp3.MoonlightHotelAndSpa.converter.ContactUsConverter;
import com.bootcamp3.MoonlightHotelAndSpa.dto.ContactUsRequest;
import com.bootcamp3.MoonlightHotelAndSpa.model.ContactUs;
import com.bootcamp3.MoonlightHotelAndSpa.service.ContactUsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Contacts", description = "ContactUs Us Page")
@CrossOrigin()
public class ContactUsController {

    private final ContactUsService contactUsService;

    @Autowired
    public ContactUsController(ContactUsService contactUsService) {
        this.contactUsService = contactUsService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> sendNewMessage(@RequestBody ContactUsRequest request) {

        ContactUs contact = ContactUsConverter.convertToContactUs(request);
        contactUsService.save(contact);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
