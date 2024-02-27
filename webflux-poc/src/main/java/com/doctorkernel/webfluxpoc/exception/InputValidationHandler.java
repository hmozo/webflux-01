package com.doctorkernel.webfluxpoc.exception;

import com.doctorkernel.webfluxpoc.DTO.InputFailedValidationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {
    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleInputValidationException(InputValidationException ex){
        InputFailedValidationResponse response= new InputFailedValidationResponse();
        response.setInput(ex.getInput());
        response.setMessage(InputValidationException.MSG);
        response.setErrorCode(InputValidationException.ERROR_CODE);
        return ResponseEntity.badRequest().body(response);
    }

}
