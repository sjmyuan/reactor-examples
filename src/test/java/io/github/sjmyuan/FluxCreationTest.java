package io.github.sjmyuan;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxCreationTest {


    @Test
    public void canBeCreatedFromNumber() {
        StepVerifier.create(Flux.just(1)).expectNext(1).verifyComplete();
        StepVerifier.create(Flux.just(1.0)).expectNext(1.0).verifyComplete();
    }

    @Test
    public void canBeCreatedFromString() {
        StepVerifier.create(Flux.just("hello world")).expectNext("hello world").verifyComplete();
    }

    @Test
    public void canBeCreatedFromArray() {
        StepVerifier.create(Flux.fromArray(new Integer[] { 1, 2, 3, 4, 5 }))
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyComplete();
    }

}
