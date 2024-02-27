package com.doctorkernel.webfluxpoc.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@RequiredArgsConstructor
public class Response {
    private Date date= new Date();
    private final int output;
}
