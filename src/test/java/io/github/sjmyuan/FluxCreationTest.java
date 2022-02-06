package io.github.sjmyuan;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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
    public void canBeCreatedFromASeriesOfString() {

        StepVerifier.create(Flux.just("hello", "world", "!!")).expectNext("hello")
                .expectNext("world").expectNext("!!").verifyComplete();
    }

    @Test
    public void canBeCreatedFromArray() {
        StepVerifier.create(Flux.fromArray(new Integer[] { 1, 2, 3, 4, 5 })).expectNext(1)
                .expectNext(2).expectNext(3).expectNext(4).expectNext(5).verifyComplete();
    }

    @Test
    public void canBeCreatedFromList() {
        List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        StepVerifier.create(Flux.fromIterable(list)).expectNext(1).expectNext(2).expectNext(3)
                .expectNext(4).verifyComplete();
    }

    @Test
    public void canBeCreatedFromSet() {
        Set<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(2);
        set.add(3);
        set.add(4);

        StepVerifier.create(Flux.fromIterable(set)).expectNext(1).expectNext(2).expectNext(3)
                .expectNext(4).verifyComplete();
    }

    @Test
    public void canBeCreatedFromStream() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        StepVerifier.create(Flux.fromStream(stream)).expectNext(1).expectNext(2).expectNext(3).expectNext(4)
                .expectNext(5).verifyComplete();
    }

    @Test
    public void canBeCreatedFromThrowable() {
        StepVerifier.create(Flux.error(new Exception("some error")))
                .verifyErrorMessage("some error");
    }

    @Test
    public void canGenerateARangeOfInteger() {
        StepVerifier.create(Flux.range(1, 3)).expectNext(1).expectNext(2).expectNext(3).verifyComplete();
    }

    @Test
    public void canGenerateEmpty() {
        StepVerifier.create(Flux.empty()).verifyComplete();
    }
}
