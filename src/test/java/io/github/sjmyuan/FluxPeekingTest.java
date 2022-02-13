package io.github.sjmyuan;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxPeekingTest {

    private Flux<Integer> flux;

    @Before
    public void beforeEach() {
        flux = Flux.just(1, 2, 3, 4, 5, 6);
    }

    @Test
    public void canDoSomethingForEveryElement() {
        List<Integer> list = new LinkedList<Integer>();
        flux.doOnNext(x -> list.add(x)).collectList().block();
        assertThat(list.size()).isEqualTo(6);
    }

    @Test
    public void canMonitorTheStatusOfEachElement() {
        List<Boolean> list = new LinkedList<Boolean>();
        flux.doOnEach((signal) -> {
            list.add(signal.isOnComplete());
        }).collectList().block();
        assertThat(list.size()).isEqualTo(7);
        assertThat(list.get(0)).isFalse();
        assertThat(list.get(1)).isFalse();
        assertThat(list.get(2)).isFalse();
        assertThat(list.get(3)).isFalse();
        assertThat(list.get(4)).isFalse();
        assertThat(list.get(5)).isFalse();
        assertThat(list.get(6)).isTrue();
    }


    @Test
    public void canDoSomethingWhenStreamComplete() {
        List<Integer> list = new LinkedList<Integer>();
        flux.doOnNext(x -> list.add(x)).doOnComplete(() -> list.add(8)).collectList().block();
        assertThat(list.size()).isEqualTo(7);
        assertThat(list.get(6)).isEqualTo(8);
    }

    @Test
    public void canDoSomethingBeforeSequenceIsSubscribed() {
        List<Integer> list = new LinkedList<Integer>();
        flux.doOnNext(x -> list.add(x)).doFirst(() -> list.add(1)).doFirst(() -> list.add(2))
                .doFirst(() -> list.add(3)).collectList().block();
        assertThat(list.size()).isEqualTo(9);
        assertThat(list.get(0)).isEqualTo(3);
        assertThat(list.get(1)).isEqualTo(2);
        assertThat(list.get(2)).isEqualTo(1);
        assertThat(list.get(3)).isEqualTo(1);
        assertThat(list.get(4)).isEqualTo(2);
        assertThat(list.get(5)).isEqualTo(3);
        assertThat(list.get(6)).isEqualTo(4);
        assertThat(list.get(7)).isEqualTo(5);
        assertThat(list.get(8)).isEqualTo(6);
    }

    @Test
    public void canDoSomethingOnError() {

        List<Integer> list = new LinkedList<Integer>();

        Flux<Integer> flux2 = Flux.<Integer>error(new RuntimeException("error")).doOnError(
                exception -> exception instanceof RuntimeException, exception -> list.add(1));

        StepVerifier.create(flux2).verifyError();
        assertThat(list.size()).isEqualTo(1);

    }

    @Test
    public void canDoSomethingFinally() {

        List<Integer> list = new LinkedList<Integer>();
        flux.doOnNext(x -> list.add(x)).doFinally(signal -> list.add(1))
                .doFinally(signal -> list.add(2)).doFinally(signal -> list.add(3)).collectList()
                .block();
        assertThat(list.size()).isEqualTo(9);
        assertThat(list.get(0)).isEqualTo(1);
        assertThat(list.get(1)).isEqualTo(2);
        assertThat(list.get(2)).isEqualTo(3);
        assertThat(list.get(3)).isEqualTo(4);
        assertThat(list.get(4)).isEqualTo(5);
        assertThat(list.get(5)).isEqualTo(6);
        assertThat(list.get(6)).isEqualTo(3);
        assertThat(list.get(7)).isEqualTo(2);
        assertThat(list.get(8)).isEqualTo(1);

        list.clear();
        StepVerifier
                .create(Flux.error(new RuntimeException("error")).doFinally(signal -> list.add(-1)))
                .verifyError();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0)).isEqualTo(-1);
    }

    @Test
    public void canPrintTheLogOfEvent() {
        StepVerifier.create(flux.log()).expectNextCount(6).verifyComplete();
    }
}
