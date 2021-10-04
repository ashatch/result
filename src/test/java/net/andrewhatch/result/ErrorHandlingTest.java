package net.andrewhatch.result;

import org.junit.jupiter.api.Test;

import static net.andrewhatch.result.Core.attempt;
import static net.andrewhatch.result.ErrorHandling.tryTo;
import static net.andrewhatch.result.Result.failure;
import static net.andrewhatch.result.Result.success;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Fail.fail;

class ErrorHandlingTest {
  @Test
  void tryToTest() {
    success("foo", String.class)
        .then(
            attempt(
                tryTo(Integer::parseInt, ex -> "could not convert to integer")))
        .either(
            s -> fail("failed"),
            f -> assertThat(f).isEqualTo("could not convert to integer")
        );

    failure("foo", String.class)
        .then(
            attempt(
                tryTo(Integer::parseInt, ex -> "could not convert to integer")))
        .either(
            s -> fail("failed"),
            f -> assertThat(f).isEqualTo("foo")
        );
  }
}
