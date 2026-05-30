package com.bigbrutus.sucursales.controller;

import com.bigbrutus.sucursales.dto.ErrorDTO;
import com.bigbrutus.sucursales.exception.BadNameException;
import com.bigbrutus.sucursales.exception.NotFoundException;
import com.bigbrutus.sucursales.exception.RequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    // Bad Request 400
    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<ErrorDTO> requestExceptionHandler(RequestException ex){
        ErrorDTO error = ErrorDTO.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Not Found 404
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> notFoundExceptionHandler(NotFoundException ex){
        ErrorDTO error = ErrorDTO.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    // Nombre mal ingresado
    @ExceptionHandler(BadNameException.class)
    public ResponseEntity<ErrorDTO> badNameExceptionHandler(BadNameException ex){
        ErrorDTO error = ErrorDTO.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

}
