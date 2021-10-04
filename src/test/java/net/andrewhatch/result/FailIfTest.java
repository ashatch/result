package net.andrewhatch.result;

import org.junit.jupiter.api.Test;

import static net.andrewhatch.result.Core.attempt;
import static net.andrewhatch.result.FailIf.failIfFalse;
import static net.andrewhatch.result.FailIf.failIfNull;
import static net.andrewhatch.result.Result.failure;
import static net.andrewhatch.result.Result.success;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class FailIfTest {
  @Test
  void failIfFalseTest() {
    success("foo")
        .then(attempt(failIfFalse(x -> false, "detected a false!")))
        .either(
            s -> fail("failed"),
            f -> assertThat(f).isEqualTo("detected a false!")
        );

    success("foo")
        .then(attempt(failIfFalse(x -> true, "detected a false!")))
        .either(
            s -> assertThat(s).isEqualTo("foo"),
            f -> fail("failed")
        );

    failure("foo")
        .then(attempt(failIfFalse(x -> false, "detected a false!")))
        .either(
            s -> fail("failed"),
            f -> assertThat(f).isEqualTo("foo")
        );

    failure("foo")
        .then(attempt(failIfFalse(x -> true, "detected a false!")))
        .either(
            s -> fail("failed"),
            f -> assertThat(f).isEqualTo("foo")
        );
  }

  @Test
  void failIfNullTest() {
    success("foo")
        .then(attempt(failIfNull(x -> null, "detected null!")))
        .either(
            s -> fail("failed"),
            f -> assertThat(f).isEqualTo("detected null!")
        );

    success("foo")
        .then(attempt(failIfNull(x -> "bar", "detected null!")))
        .either(
            s -> assertThat(s).isEqualTo("bar"),
            f -> fail("failed")
        );

    failure("foo")
        .then(attempt(failIfNull(x -> null, "detected null!")))
        .either(
            s -> fail("failed"),
            f -> assertThat(f).isEqualTo("foo")
        );

    failure("foo")
        .then(attempt(failIfNull(x -> "bar", "detected null!")))
        .either(
            s -> fail("failed"),
            f -> assertThat(f).isEqualTo("foo")
        );
  }
}
