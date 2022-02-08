package io.github.sjmyuan;

import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuples;

public class FluxOperationTest {

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
}
