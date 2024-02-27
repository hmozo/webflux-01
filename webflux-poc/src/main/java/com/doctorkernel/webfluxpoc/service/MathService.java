package com.doctorkernel.webfluxpoc.service;

import com.doctorkernel.webfluxpoc.DTO.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class MathService {
    public Response findSquare(int input){
        return new Response(input*input);
    }

    public List<Response> multiplicationTable(int input){
        return IntStream.rangeClosed(1, 10)
                .peek(i->SleepUtil.sleepSeconds(1))
                .peek(i-> System.out.println("Processing: " + i))
                .mapToObj(i->new Response(input*i))
                .toList();
    }
}
