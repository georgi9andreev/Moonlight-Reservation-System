package com.bootcamp3.MoonlightHotelAndSpa.service;

import java.util.Map;

public interface EmailService {

    void sendEmail(String to, String subject, String text);

    void sendHtmlEmail(String to, String subject, Map<String, Object> model);
}
