package io.github.sjmyuan;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTransformingTest {


    @Test
    public void canDoFilter() {
        StepVerifier.create(Mono.just(6).filter(x -> x > 5)).expectNext(6).verifyComplete();
        StepVerifier.create(Mono.just(2).filter(x -> x > 5)).verifyComplete();
    }

    @Test
    public void canMapTheTypeOfValueInSequenceToAnotherType() {
        StepVerifier.create(Mono.just(1).map(x -> x.toString())).expectNext("1").verifyComplete();
    }

    @Test
    public void canMapTheValueInSequenceToAnotherValue() {
        StepVerifier.create(Mono.just(1).map(x -> x + 1)).expectNext(2).verifyComplete();
    }

    @Test
    public void canMapTheValueInSequenceToAnotherSequence() {

        StepVerifier.create(Mono.just(1).flatMapMany(x -> Flux.just(x, x))).expectNext(1)
                .expectNext(1).verifyComplete();

        StepVerifier.create(Mono.just(1).flatMap(x -> Mono.just(x + 1))).expectNext(2)
                .verifyComplete();
    }

    @Test
    public void canRecoverWithSingleDefaultValueFromEmptySequence() {
        StepVerifier.<Integer>create(Mono.<Integer>empty().defaultIfEmpty(1)).expectNext(1)
                .verifyComplete();
    }

    @Test
    public void canRecoverWithAnotherSequenceFromEmptySequence() {
        StepVerifier.<Integer>create(Mono.<Integer>empty().switchIfEmpty(Mono.just(1)))
                .expectNext(1).verifyComplete();
    }
}
