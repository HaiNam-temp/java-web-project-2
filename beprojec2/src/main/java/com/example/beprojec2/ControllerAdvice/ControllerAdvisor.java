package com.example.beprojec2.ControllerAdvice;

import com.example.beprojec2.ErrorDTO.UserErrorDTO;

import com.example.beprojec2.customException.FieldRequiredException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<Object>  handleValidationException(ArithmeticException ex) {
        UserErrorDTO userErrorDTO = new UserErrorDTO();
        userErrorDTO.setErrorResonse(ex.getMessage());
        userErrorDTO.setDetail("Loi toan hoc ");
        return new ResponseEntity<>(userErrorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(FieldRequiredException.class)
    public ResponseEntity<Object>  handleFieldRequiredException(FieldRequiredException ex) {
        UserErrorDTO userErrorDTO = new UserErrorDTO();
        userErrorDTO.setErrorResonse(ex.getMessage());
        userErrorDTO.setDetail("ten khong duoc de trong ! ");
        return new ResponseEntity<>(userErrorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
