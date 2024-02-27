package com.doctorkernel.webfluxpoc.controller;

import com.doctorkernel.webfluxpoc.DTO.MultiplyRequestDTO;
import com.doctorkernel.webfluxpoc.DTO.Response;
import com.doctorkernel.webfluxpoc.exception.InputValidationException;
import com.doctorkernel.webfluxpoc.service.ReactiveMathService;
import com.doctorkernel.webfluxpoc.service.SinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reactive-math")
public class ReactiveMapController {
    @Autowired
    private ReactiveMathService reactiveMathService;
    @Autowired
    private SinksService sinksService;

    @GetMapping("/square/{input}")
    public Mono<Response> findSquare(@PathVariable int input){
        return Mono.just(input)
                .handle((integer, sink)->{
                    if(integer>=10 && integer<=20){
                        sink.next(integer);
                    }else{
                        sink.error(new InputValidationException(integer));
                    }})
                .cast(Integer.class)
                .flatMap(i->reactiveMathService.findSquare(i));
    }

    @GetMapping(value = "/table/{input}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTable(@PathVariable int input){
        return reactiveMathService.multiplicationTable(input);
    }

    @PostMapping("/multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDTO> monoDto){
        return reactiveMathService.multiply(monoDto);
    }

    @GetMapping(value = "/flux-int", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> intFlux(){
        List<Integer> list= new ArrayList<>();

        return sinksService.sinksThreadSafe()
                .doOnNext(System.out::println)
                .doOnNext((value->list.add((Integer)value)))
                .doOnComplete(()->System.out.println("List: " + list.size()));
    }
}
