package com.volvocars.spring.reactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class FluxAndMonoController {
    @GetMapping("/flux")
    public Flux<Integer> returnFlux() {
        return Flux.just(1, 2, 3, 4, 5)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> returnFluxStream() {
        return Flux.just(1, 2, 3, 4, 5)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }
    @GetMapping(value = "/fluxLongStream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> returnFluxLongStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .log();
    }
    @GetMapping(value = "/mono")
    public Mono<Long> returnMono() {
        return Mono.just(1l)
                .log();
    }
}