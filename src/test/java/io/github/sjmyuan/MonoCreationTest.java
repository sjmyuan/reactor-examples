
package io.github.sjmyuan;

import java.util.Optional;
import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoCreationTest {
    @Test
    public void canBeCreatedFromNullableValue() {
        String value = null;
        StepVerifier.create(Mono.justOrEmpty(value)).verifyComplete();

        value = "hello world";
        StepVerifier.create(Mono.justOrEmpty(value)).expectNext("hello world").verifyComplete();
    }

    @Test
    public void canBeCreatedFromOptionalValue() {

        Optional<String> value = Optional.empty();
        StepVerifier.create(Mono.justOrEmpty(value)).verifyComplete();

        value = Optional.of("hello world");
        StepVerifier.create(Mono.justOrEmpty(value)).expectNext("hello world").verifyComplete();
    }
}
