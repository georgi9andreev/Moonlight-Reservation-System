package com.bootcamp3.MoonlightHotelAndSpa.util;

import com.bootcamp3.MoonlightHotelAndSpa.model.RoomReservation;
import com.bootcamp3.MoonlightHotelAndSpa.service.EmailService;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoomReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
public class RoomReservationScheduler {

    private static final Logger log = LoggerFactory.getLogger(RoomReservationScheduler.class);

    public static final long DAYS_BEFORE_RESERVATION = 2L;

    private final RoomReservationService roomReservationService;
    private final EmailService emailService;

    @Autowired
    public RoomReservationScheduler(RoomReservationService roomReservationService, EmailService emailService) {
        this.roomReservationService = roomReservationService;
        this.emailService = emailService;
    }

    //Checks bookings every day at noon and sends an email to those starting in 2 days.
    @Scheduled(cron = "0 0 12 * * ?")
    //@Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void sendReminderEmailForRoomReservation() throws ParseException {
        log.info("Checking reservations...");

        List<RoomReservation> roomReservationsList = roomReservationService.getAll();

        if (roomReservationsList.size() > 0) {

            checkRoomReservationDateForDeadline(roomReservationsList);
        }
    }

    private void checkRoomReservationDateForDeadline(List<RoomReservation> roomReservationsList) throws ParseException {

        for (RoomReservation rr : roomReservationsList) {

            long differenceBetweenDates = calculateDifferenceBetweenDates(rr.getCheckIn());
            boolean isReservationActive = rr.getCheckIn().isAfter(Instant.now());

            if (isReservationActive && differenceBetweenDates <= DAYS_BEFORE_RESERVATION) {
                sendEmail(rr, differenceBetweenDates);
            }
        }
    }

    private void sendEmail(RoomReservation roomReservation, long differenceBetweenDates) {

        String to = roomReservation.getUser().getEmail();
        String subject = "You reservation starts soon";

        String emailText = String.format("Hello, %s, your reservation for Moonlight Hotel and Spa starts in %d days."
                , roomReservation.getUser().getFirstName(), differenceBetweenDates);

        if (differenceBetweenDates == 0) {
            emailText = String.format("Hello, %s, your reservation for Moonlight Hotel and Spa start today."
                    , roomReservation.getUser().getFirstName());
        }

        emailService.sendEmail(to, subject, emailText);
    }

    private long calculateDifferenceBetweenDates(Instant reservationDate) throws ParseException {

        return Duration.between(Instant.now(), reservationDate).toDays();
    }
}
