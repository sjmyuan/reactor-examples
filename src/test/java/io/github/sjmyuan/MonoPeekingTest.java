package io.github.sjmyuan;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import reactor.core.publisher.Mono;

public class MonoPeekingTest {

    @Test
    public void canDoSomethingForEveryElement() {
        List<Integer> list = new LinkedList<Integer>();
        Mono.just(1).doOnNext(x -> list.add(x)).block();
        assertThat(list.size()).isEqualTo(1);

        Mono.just(1).doOnNext(x -> System.out.println(x)).block();
    }
}
