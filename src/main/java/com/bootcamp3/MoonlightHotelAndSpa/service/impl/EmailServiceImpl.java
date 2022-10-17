package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.exception.EmailNotSentException;
import com.bootcamp3.MoonlightHotelAndSpa.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.EmailConstant.*;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final Configuration configuration;
    private static final Logger logger = LogManager.getLogger(Logger.class);

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, Configuration configuration) {
        this.javaMailSender = javaMailSender;
        this.configuration = configuration;
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public void sendEmail(String to, String subject, String text) {

        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(EMAIL_SET_FROM);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            javaMailSender.send(message);

            logger.info(EMAIL_SENT_SUCCESSFULLY);
        } catch (EmailNotSentException ex) {

            logger.error(EMAIL_ERROR);
        }
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public void sendHtmlEmail(String to, String subject, Map<String, Object> model) {

        try {

            Template t = configuration.getTemplate("payment-receipt-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            helper.addAttachment("logo.jpeg", new ClassPathResource("logo.jpeg"));

            helper.setSubject(subject);
            helper.setFrom(EMAIL_SET_FROM);
            helper.setTo(to);

            helper.setText(html, true);

            javaMailSender.send(message);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
