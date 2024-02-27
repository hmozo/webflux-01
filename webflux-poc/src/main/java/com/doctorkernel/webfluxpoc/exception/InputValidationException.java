package com.doctorkernel.webfluxpoc.exception;

import lombok.Getter;

@Getter
public class InputValidationException extends RuntimeException{
    public static final int ERROR_CODE= 100;
    public static final String MSG= "Allowed range: 10-20";
    private final int input;

    public InputValidationException(int input){
        super(MSG);
        this.input= input;
    }
}
