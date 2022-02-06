
package io.github.sjmyuan;

import reactor.core.publisher.Mono;
import org.junit.Test;

import reactor.test.StepVerifier;

public class MonoCreationTest {
    @Test
    public void canBeCreatedFromNullableValue() {
        String value = null;
        StepVerifier.create(Mono.just(value)).verifyComplete();
    }
}