package io.github.sjmyuan;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import reactor.core.publisher.Mono;

public class MonoUnboxingTest {

    @Test
    public void canBeConvertedToValue() {
        assertThat(Mono.just(1).block()).isEqualTo(1);
        assertThat(Mono.empty().block()).isNull();
    }

}
