package io.github.sjmyuan;

import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuples;

public class FluxTransformTest {

    private Flux<Integer> flux;

    @Before
    public void beforeEach() {
        flux = Flux.just(1, 2, 3, 4, 5, 6);
    }

    @Test
    public void canDoFilter() {
        StepVerifier.create(flux.filter(x -> x > 5)).expectNext(6).verifyComplete();
    }

    @Test
    public void canDoZipWithIterable() {
        List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);

        StepVerifier.create(flux.zipWithIterable(list)).expectNext(Tuples.of(1, 1))
                .expectNext(Tuples.of(2, 2)).expectNext(Tuples.of(3, 3)).verifyComplete();
    }

    @Test
    public void canDoZipWithFlux() {
        Flux<Integer> flux2 = Flux.just(1, 2, 3);
        StepVerifier.create(flux.zipWith(flux2)).expectNext(Tuples.of(1, 1))
                .expectNext(Tuples.of(2, 2)).expectNext(Tuples.of(3, 3)).verifyComplete();
    }

    @Test
    public void canDoZipWithMono() {
        Mono<Integer> mono = Mono.just(1);
        StepVerifier.create(flux.zipWith(mono)).expectNext(Tuples.of(1, 1)).verifyComplete();
    }

    @Test
    public void canDoZipWithCustomFunction() {
        Flux<Integer> flux2 = Flux.just(1, 2, 3);
        StepVerifier.create(flux.zipWith(flux2, (x, y) -> x + y)).expectNext(2).expectNext(4)
                .expectNext(6).verifyComplete();
    }

    @Test
    public void canTakeGivenNumberElementFromFirst() {
        StepVerifier.create(flux.take(1)).expectNext(1).verifyComplete();
    }

    @Test
    public void canTakeGivenNumberElementFromLast() {
        StepVerifier.create(flux.takeLast(2)).expectNext(5).expectNext(6).verifyComplete();
    }

    @Test
    public void canTakeAllElementsUntilMeetTheCondition() {
        StepVerifier.create(flux.takeUntil(x -> x > 3)).expectNext(1).expectNext(2).expectNext(3)
                .expectNext(4).verifyComplete();
    }

    @Test
    public void canTakeAllElementUntilNotMeetTheCondition() {
        StepVerifier.create(flux.takeWhile(x -> x < 3)).expectNext(1).expectNext(2)
                .verifyComplete();
    }

    @Test
    public void canTakeTheSpecifiedElement() {
        StepVerifier.create(flux.elementAt(3)).expectNext(4).verifyComplete();
    }

    @Test
    public void canEmitErrorIfEmpty() {
        StepVerifier.create(Flux.empty().last()).expectError();
    }

    @Test
    public void canEmitDefaultValueIfEmpty() {
        StepVerifier.create(Flux.empty().last(10)).expectNext(10).verifyComplete();
    }

    @Test
    public void canSkipGivenNumberOfElement() {
        StepVerifier.create(flux.skip(5)).expectNext(6).verifyComplete();
    }

    @Test
    public void canSkipGivenNumberOfLastElement() {
        StepVerifier.create(flux.skipLast(5)).expectNext(1).verifyComplete();
    }

    @Test
    public void canSkipElementUntilMeetingCondition() {
        StepVerifier.create(flux.skipUntil(x -> x > 4)).expectNext(5).expectNext(6)
                .verifyComplete();
    }

    @Test
    public void canSkipElementUntilNotMeetingCondition() {
        StepVerifier.create(flux.skipWhile(x -> x < 5)).expectNext(5).expectNext(6)
                .verifyComplete();
    }

    @Test
    public void canCheckIfSequenceOnlyOneElement() {
        StepVerifier.create(flux.single()).expectError();
        StepVerifier.create(Flux.empty().single(2)).expectNext(2).verifyComplete();
        StepVerifier.create(Flux.just(1).single()).expectNext(1).verifyComplete();
    }

    @Test
    public void canRecoverFromError() {
        StepVerifier.<Integer>create(
                Flux.<Integer>error(new RuntimeException("error")).onErrorResume(e -> Flux.just(1)))
                .expectNext(1).verifyComplete();
    }

    @Test
    public void canRecoverFromEmptySequence() {
        StepVerifier.<Integer>create(Flux.<Integer>empty().switchIfEmpty(Flux.just(1)))
                .expectNext(1).verifyComplete();
    }

    @Test
    public void canMapTheTypeOfValueInSequenceToAnotherType() {
        StepVerifier.create(flux.map(x -> x.toString())).expectNext("1").expectNext("2")
                .expectNext("3").expectNext("4").expectNext("5").expectNext("6").verifyComplete();
    }

    @Test
    public void canMapTheValueInSequenceToAnotherValue() {
        StepVerifier.create(flux.map(x -> x + 1)).expectNext(2).expectNext(3).expectNext(4)
                .expectNext(5).expectNext(6).expectNext(7).verifyComplete();
    }

    @Test
    public void canMapTheValueInSequenceToAnotherSequence() {

        StepVerifier.create(flux.flatMap(x -> Flux.just(x, x))).expectNext(1).expectNext(1)
                .expectNext(2).expectNext(2).expectNext(3).expectNext(3).expectNext(4).expectNext(4)
                .expectNext(5).expectNext(5).expectNext(6).expectNext(6).verifyComplete();

        StepVerifier.create(flux.flatMap(x -> Mono.just(x))).expectNext(1).expectNext(2)
                .expectNext(3).expectNext(4).expectNext(5).expectNext(6).verifyComplete();
    }
}
