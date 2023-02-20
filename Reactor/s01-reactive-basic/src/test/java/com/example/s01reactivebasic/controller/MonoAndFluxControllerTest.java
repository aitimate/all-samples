package com.example.s01reactivebasic.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@WebFluxTest
@AutoConfigureWebTestClient(timeout = "10000") // 默认5秒超时，修改为10秒
public class MonoAndFluxControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void flux() {
        Flux<Integer> responseBody = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                // 发送
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();
        responseBody.subscribe(System.out::println);
    }

    @Test
    public void fluxStream() throws InterruptedException {
        Flux<Integer> responseBody = webTestClient.get().uri("/flux/stream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                // 发送
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();
        responseBody.subscribe(System.out::println);
        Thread.sleep(5000);
    }

    @Test
    public void fluxStreamInfinite() {
        Flux<Long> responseBody = webTestClient.get().uri("/flux/stream/infinite")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();
        StepVerifier.create(responseBody)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .thenCancel()
                .verify();
    }
}
