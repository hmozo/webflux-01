package com.doctorkernel.webfluxpoc;

import com.doctorkernel.webfluxpoc.service.SinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class WebfluxPocApplication implements CommandLineRunner {
	@Autowired
	private SinksService sinksService;

	public static void main(String[] args) {
		SpringApplication.run(WebfluxPocApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Mono<String> monoString= sinksService.sinkMultipleSubscribers();
		System.out.println("Received2: " + monoString.block());

		Flux<String> fluxString= sinksService.sinkFluxUnicast();
		fluxString.doOnNext(System.out::println).blockLast();
		fluxString.doOnNext(System.out::println).blockLast();

		sinksService.sinksThreadSafe();

	}
}
