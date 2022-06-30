package com.bootcamp3.MoonlightHotelAndSpa.exception;

import com.bootcamp3.MoonlightHotelAndSpa.model.ErrorField;
import com.bootcamp3.MoonlightHotelAndSpa.model.ErrorMessage;
import com.bootcamp3.MoonlightHotelAndSpa.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.ValidationConstant.VALIDATION_ERROR;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> noSuchElement(NoSuchElementException ex) {

        return createHttpResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> badCredentials(BadCredentialsException ex) {

        return createHttpResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFound(UserNotFoundException ex) {

        return createHttpResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPhoneNumber.class)
    public ResponseEntity<ErrorMessage> invalidPhoneNumber(InvalidPhoneNumber ex) {

        return createHttpResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        Set<String> messages = new HashSet<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream()
                .map(constraintViolation -> String.format(String.valueOf(constraintViolation.getPropertyPath()),
                        constraintViolation.getMessage()))
                .collect(Collectors.toList()));

        return createHttpErrorResponse(messages);
    }

    private ResponseEntity<ErrorMessage> createHttpResponse(String message, HttpStatus httpStatus) {

        return new ResponseEntity<>(new ErrorMessage(message), httpStatus);
    }

    private ResponseEntity<ErrorResponse> createHttpErrorResponse(Set<String> messages) {

        ErrorField error = new ErrorField(messages);

        ErrorResponse errorResponse = new ErrorResponse(VALIDATION_ERROR, error);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
