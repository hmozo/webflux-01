package com.doctorkernel.webfluxpoc.service;

import com.doctorkernel.webfluxpoc.DTO.MultiplyRequestDTO;
import com.doctorkernel.webfluxpoc.DTO.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ReactiveMathService {
    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(()->input*input)
                .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input){
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                //.doOnNext(i -> SleepUtil.sleepSeconds(1))
                .doOnNext(i -> System.out.println("Processing: " + i))
                .map(i -> new Response(i*input));
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDTO> monoDto){
        return monoDto
                .map(dto->dto.getFirst()*dto.getSecond())
                .map(Response::new);
    }
}
