package com.volvocars.spring.reactive.application;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {
    @Test
    public void flusTest(){
         Flux<String> stringFlux= Flux.just("Spring","Spring Boot","Reactive Spring")
                 .concatWith(Flux.error(new RuntimeException("Exception Occured")))
                 .concatWith(Flux.just("After Error"))
                 .log();
         stringFlux.subscribe(System.out::println,(e) ->System.err.println(e),()-> System.out.println("Completed"));
    }
    @Test
    public void fluxTestElements_WithoutError(){
        Flux<String> stringFlux= Flux.just("Spring","Spring Boot","Reactive Spring")
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();

    }
    @Test
    public void fluxTestElementsWithError(){
        Flux<String> stringFlux= Flux.just("Spring","Spring Boot","Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occured")))
                .concatWith(Flux.just("After Error"))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("Exception Occured")
                .verify();

    }
    @Test
    public void fluxTestNextElementCount(){
        Flux<String> stringFlux= Flux.just("Spring","Spring Boot","Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occured")))
                .concatWith(Flux.just("After Error"))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectError(RuntimeException.class)
                .verify();
    }
    @Test
    public void fluxTestSingleNext(){
        Flux<String> stringFlux= Flux.just("Spring","Spring Boot","Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occured")))
                .concatWith(Flux.just("After Error"))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring","Spring Boot","Reactive Spring")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void monoTest(){
        Mono<String> stringMono = Mono.just("Spring")
                .log();

        StepVerifier.create(stringMono)
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void monoTest_Error(){
        StepVerifier.create(Mono.error(new RuntimeException("Exception Occured")).log())
                .expectError(RuntimeException.class)
                .verify();
    }
}
