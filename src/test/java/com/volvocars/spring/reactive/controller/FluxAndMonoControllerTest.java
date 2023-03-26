package com.volvocars.spring.reactive.controller;

import com.volvocars.spring.reactive.application.PetStoreApplication;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@WebFluxTest
@ContextConfiguration(classes= PetStoreApplication.class)
@AutoConfigureWebTestClient(timeout = "36000")
public class FluxAndMonoControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void flux_approach1(){
        Flux<Integer> integerFlux = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();
        StepVerifier.create(integerFlux)
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyComplete();
    }

    @Test
    public void flux_approach2(){
        webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Integer.class)
                .hasSize(5);
    }
    @Test
    public void flux_approach3(){
        List<Integer> expectedIntegerList = Arrays.asList(1,2,3,4,5);

        EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();

        Assert.assertEquals(expectedIntegerList,entityExchangeResult.getResponseBody());
    }
    @Test
    public void flux_approach4(){
        List<Integer> expectedIntegerList = Arrays.asList(1,2,3,4,5);

        webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .consumeWith((response)->{
                    Assert.assertEquals(expectedIntegerList,response.getResponseBody());
                });
    }
    @Test
    public void fluxLongStream()
    {
        Flux<Long> longFlux = webTestClient.get().uri("/fluxLongStream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(longFlux)
                .expectNext(0l)
                .expectNext(1l)
                .expectNext(2l)
                .thenCancel()
                .verify();

    }
    @Test
    public void mono(){
        Integer expectedValue = 1;
        webTestClient.get().uri("/mono")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .consumeWith((response) ->{
                    Assert.assertEquals(expectedValue,response.getResponseBody());
                });
    }
}
