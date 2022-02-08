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
    public void canDoSideEffectForEveryElement() {
        List<Integer> list = new LinkedList<Integer>();
        flux.doOnNext(x -> list.add(x)).collectList().block();
        assertThat(list.size()).isEqualTo(6);
    }
}
