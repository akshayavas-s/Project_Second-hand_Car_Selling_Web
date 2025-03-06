package com.hms.exception;

import com.hms.payload.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(ResourceExistsException.class)
    public ResponseEntity<?> resourceExists(
            ResourceExistsException r,
            WebRequest req
    ){
        ErrorDto dto = new ErrorDto();
        dto.setDate(new Date());
        dto.setMessage(r.getMessage());
        dto.setRequest(req.getDescription(false));
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFound(
            ResourceNotFoundException r,
            WebRequest req
    ){
        ErrorDto dto = new ErrorDto();
        dto.setDate(new Date());
        dto.setMessage(r.getMessage());
        dto.setRequest(req.getDescription(false));
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
