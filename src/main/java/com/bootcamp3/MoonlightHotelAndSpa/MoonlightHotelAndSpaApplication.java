package com.bootcamp3.MoonlightHotelAndSpa;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@SecurityScheme(name = "bearerAuth", scheme = "bearer", type = SecuritySchemeType.HTTP)
@EnableScheduling
public class MoonlightHotelAndSpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoonlightHotelAndSpaApplication.class, args);
	}
}
