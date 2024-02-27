package com.doctorkernel.webfluxpoc.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SinksService {
    public Mono<String> sinkMultipleSubscribers(){
        var sink= Sinks.one();
        sink.asMono().subscribe(value-> System.out.println("Received: " + value));
        sink.emitValue("a-02154",((signalType, emitResult) -> {
            System.out.println(signalType.name());
            System.out.println(emitResult.name());
            return false;
        }));

        /*sink.emitValue("a-02155",((signalType, emitResult) -> {
            System.out.println(signalType.name());
            System.out.println(emitResult.name());
            return false;
        }));*/
        return sink.asMono().cast(String.class);
    }

    public Flux<String> sinkFluxUnicast(){
        var sink= Sinks.many().multicast().onBackpressureBuffer();
        sink.tryEmitNext("a-412");
        sink.tryEmitNext("a-568");
        sink.tryEmitComplete();

        return sink.asFlux().cast(String.class);
    }

    public Flux<Integer> sinksThreadSafe(){
        var sink= Sinks.many().multicast().onBackpressureBuffer();

        for (int i=0; i<15; i++){
            final Integer j= i;
            CompletableFuture.runAsync(()->{
                sink.emitNext(j, (s, e)->true);
                if(j==14){
                    sink.tryEmitComplete();
                }
            });
        }

        List<Integer> list= new ArrayList<>();
        /*sink.asFlux()
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(System.out::println)
                .doOnComplete(()->System.out.println("List: " + list.size()))
                .subscribe(value->list.add((Integer)value));*/

        return sink.asFlux()
                .delayElements(Duration.ofSeconds(1))
                .cast(Integer.class);
    }
}
