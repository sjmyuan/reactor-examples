package io.github.sjmyuan;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Test;
import reactor.core.publisher.Flux;

public class FluxUnwrapTest {

    @Test
    public void canBeConvertedToList() {
        List<Integer> list = Flux.just(1, 2, 3).collectList().block();
        assertThat(list.size()).isEqualTo(3);
        assertThat(list.get(0)).isEqualTo(1);
        assertThat(list.get(1)).isEqualTo(2);
        assertThat(list.get(2)).isEqualTo(3);
    }

    @Test
    public void canBeConvertedToStream() {
        Stream<Integer> stream = Flux.just(1, 2, 3).toStream();
        assertThat(stream.count()).isEqualTo(3);
    }

    @Test
    public void canBeConvertedToIterable() {
        Iterable<Integer> iterable = Flux.just(1, 2, 3).toIterable();
        Iterator<Integer> iter = iterable.iterator();
        assertThat(iter.next()).isEqualTo(1);
        assertThat(iter.next()).isEqualTo(2);
        assertThat(iter.next()).isEqualTo(3);
        assertThat(iter.hasNext()).isFalse();
    }

}
