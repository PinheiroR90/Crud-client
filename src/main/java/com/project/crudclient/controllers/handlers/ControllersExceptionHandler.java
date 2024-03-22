package com.project.crudclient.controllers.handlers;

import com.project.crudclient.dto.CustomError;
import com.project.crudclient.dto.FieldMessage;
import com.project.crudclient.dto.ValidateError;
import com.project.crudclient.services.exceptions.DatabaseException;
import com.project.crudclient.services.exceptions.NotFoundClientException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> database(DatabaseException e,HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        CustomError err = new CustomError(Instant.now(),status.value(),e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e,HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidateError err = new ValidateError(Instant.now(),status.value(),"Dados inválidos!",request.getRequestURI());

        for(FieldError f: e.getBindingResult().getFieldErrors()){
            err.addError(f.getField(),f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }
}
