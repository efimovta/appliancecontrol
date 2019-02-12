package ru.eta.appliancecontrol.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.eta.appliancecontrol.exception.OvenByIdNotFoundException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final String OVEN_BY_ID_NOT_FOUND = "Wrong oven id, there is no oven with this id.";

    @ExceptionHandler({OvenByIdNotFoundException.class})
    public ResponseEntity<String> entityNotFound() {
        return new ResponseEntity<>(OVEN_BY_ID_NOT_FOUND, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
