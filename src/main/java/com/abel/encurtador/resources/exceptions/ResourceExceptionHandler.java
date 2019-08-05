package com.abel.encurtador.resources.exceptions;

import com.abel.encurtador.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class) // Indica que é um tratador de excessoes
    public ResponseEntity<StandardError> objNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), new Date(System.currentTimeMillis()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
}
