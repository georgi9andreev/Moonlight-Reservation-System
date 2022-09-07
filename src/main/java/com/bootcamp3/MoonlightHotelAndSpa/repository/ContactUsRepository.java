package com.bootcamp3.MoonlightHotelAndSpa.repository;

import com.bootcamp3.MoonlightHotelAndSpa.model.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {
}
