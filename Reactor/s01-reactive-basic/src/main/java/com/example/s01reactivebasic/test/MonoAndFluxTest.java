package com.example.s01reactivebasic.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
* 以下内容与MongoDB无关，因此可以将`spring-boot-starter-data-mongodb-reactive`注释
* */
public class MonoAndFluxTest {
    @Test
    public void monoTest1(){
        Mono<String> mono = Mono.just("Spring").log();
        StepVerifier.create(mono)
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void fluxTest1() {
        Flux<Object> just = Flux.just("Spring", "Reactor");
        just.subscribe(System.out::println);
    }

    @Test
    public void fluxTest2() {
        Flux<String> just = Flux.just("Spring", "Reactor");
        just.subscribe(
                (item)->System.out.println("item="+item),
                (error) -> System.err.println("Exception is" + error),
                ()->System.out.println("Completed!")
        );
    }

    @Test
    public void fluxTest3() {
        Flux<String> just = Flux.just("Spring", "Reactor")
                .concatWith(Flux.error(new RuntimeException("error")))
                .concatWith(Flux.just("Here is never come"))
                .log();
        just.subscribe(System.out::println, (error) ->System.err.println("Exception is" + error));
    }

    @Test
    public void fluxTest4() {
        // Assert
        Flux<String> just = Flux.just("Spring", "Reactor").log();
        StepVerifier.create(just)
                .expectNext("Spring")
                .expectNext("Reactor")
                // .expectNext("Reactor")
                .verifyComplete();
    }


    @Test
    public void fluxTest5() {
        // Assert
        Flux<String> just = Flux.just("Spring", "Reactor")
                .concatWith(Flux.error(new RuntimeException("error")))
                .log();
        StepVerifier.create(just)
                .expectNext("Spring","Reactor")
                // .expectError(RuntimeException.class)
                .expectErrorMessage("error")
                .verify();
    }
}
