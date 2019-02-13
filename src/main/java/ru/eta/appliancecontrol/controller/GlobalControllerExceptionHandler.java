package ru.eta.appliancecontrol.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.eta.appliancecontrol.exception.HeatingModeNotFoundException;
import ru.eta.appliancecontrol.exception.OvenNotFoundException;
import ru.eta.appliancecontrol.exception.RecipeNotFoundException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final String _NOT_FOUND = " not found while perform your action. Please, check params and format of your request.";

    private static final String OVEN_NOT_FOUND = "Oven" + _NOT_FOUND;
    private static final String RECIPE_NOT_FOUND = "Recipe" + _NOT_FOUND;
    private static final String HEATING_MODE_NOT_FOUND = "'Heating mode'" + _NOT_FOUND;

    @ExceptionHandler({OvenNotFoundException.class})
    public ResponseEntity<String> ovenNotFound() {
        return notFound(OVEN_NOT_FOUND);
    }

    @ExceptionHandler({RecipeNotFoundException.class})
    public ResponseEntity<String> recipeNotFound() {
        return notFound(RECIPE_NOT_FOUND);
    }

    @ExceptionHandler({HeatingModeNotFoundException.class})
    public ResponseEntity<String> heatingModeNotFound() {
        return notFound(HEATING_MODE_NOT_FOUND);
    }

    private ResponseEntity<String> notFound(String msg) {
        return new ResponseEntity<>(msg, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
