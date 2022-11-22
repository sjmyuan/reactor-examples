
package io.github.sjmyuan;

import java.util.Optional;
import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoBoxingTest {
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

    @Test
    public void canBeCreatedFromCallable() {
        StepVerifier.create(Mono.fromCallable(() -> "hello world!")).expectNext("hello world!")
                .verifyComplete();

    }

    @Test
    public void canBeCreatedFromThrowable() {
        StepVerifier.create(Mono.error(new Exception("some error")))
                .verifyErrorMessage("some error");
    }

    @Test
    public void canBeCreatedFromString() {
        StepVerifier.create(Mono.just("hello world")).expectNext("hello world").verifyComplete();
    }

    @Test
    public void canBeCreatedFromNumber() {
        StepVerifier.create(Mono.just(1)).expectNext(1).verifyComplete();
        StepVerifier.create(Mono.just(1.0)).expectNext(1.0).verifyComplete();
    }
}
