package net.andrewhatch.result;

import org.junit.jupiter.api.Test;

import static net.andrewhatch.result.Core.onFailure;
import static net.andrewhatch.result.Core.onSuccess;
import static net.andrewhatch.result.Result.failure;
import static net.andrewhatch.result.Result.success;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class CoreTest {
  @Test
  void onSuccessTest() {
    success("foo")
        .then(onSuccess(x -> x + "!"))
        .either(
            s -> assertThat(s).isEqualTo("foo!"),
            f -> fail("failed")
        );

    failure("foo")
        .then(onSuccess(x -> x + "!"))
        .either(
            s -> fail("failed"),
            f -> assertThat(f).isEqualTo("foo")
        );
  }

  @Test
  void onFailureTest() {
    failure("foo")
        .then(onFailure(x -> x + "!"))
        .either(
            s -> fail("failed"),
            f -> assertThat(f).isEqualTo("foo!")
        );

    success("foo")
        .then(onFailure(x -> x + "!"))
        .either(
            s -> assertThat(s).isEqualTo("foo"),
            f -> fail("failed")
        );
  }
}
