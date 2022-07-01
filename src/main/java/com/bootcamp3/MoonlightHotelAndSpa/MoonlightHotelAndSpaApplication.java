package com.bootcamp3.MoonlightHotelAndSpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class MoonlightHotelAndSpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoonlightHotelAndSpaApplication.class, args);
	}
}
