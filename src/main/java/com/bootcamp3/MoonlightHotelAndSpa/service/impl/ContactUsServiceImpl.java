package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.model.ContactUs;
import com.bootcamp3.MoonlightHotelAndSpa.repository.ContactUsRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactUsServiceImpl implements ContactUsService {

    private final ContactUsRepository contactUsRepository;

    @Autowired
    public ContactUsServiceImpl(ContactUsRepository contactUsRepository) {
        this.contactUsRepository = contactUsRepository;
    }

    @Override
    public void save(ContactUs contactUs) {
        contactUsRepository.save(contactUs);
    }
}
