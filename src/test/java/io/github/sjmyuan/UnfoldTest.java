package io.github.sjmyuan;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.util.function.Function;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


public class UnfoldTest {

    /**
     * @param <S> status type
     * @param <O> element type
     * @param s status
     * @param f element
     * @return stream unfold by status
     */
    public <S, O> Flux<O> unfold(final S s, final Function<S, Mono<Tuple2<O, Option<S>>>> f) {
        return f.apply(s).flatMapMany(x -> {
            O element = x._1();
            Option<S> status = x._2();

            if (status.isEmpty()) {
                return Flux.just(element);
            }

            return Flux.merge(Flux.just(element), unfold(status.get(), f));
        });
    }


    @Test
    public void shouldUnfoldBySeed() {

        Flux<Integer> result = unfold(0, (x) -> x < 5 ? Mono.just(Tuple.of(x + 1, Option.of(x + 1)))
                : Mono.just(Tuple.of(x + 1, Option.none())));

        StepVerifier.create(result).expectNext(1).expectNext(2).expectNext(3).expectNext(4)
                .expectNext(5).expectNext(6).verifyComplete();

    }

}
