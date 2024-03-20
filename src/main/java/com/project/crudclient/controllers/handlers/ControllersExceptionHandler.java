package com.project.crudclient.controllers.handlers;

import com.project.crudclient.dto.CustomError;
import com.project.crudclient.services.exceptions.NotFoundClientException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllersExceptionHandler {

    @ExceptionHandler(NotFoundClientException.class)
    public ResponseEntity<CustomError> exceptionNotFound(NotFoundClientException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError err = new CustomError(
          Instant.now(),
          status.value(),
          e.getMessage(),
          request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }
}
