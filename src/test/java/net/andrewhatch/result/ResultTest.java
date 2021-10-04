package net.andrewhatch.result;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static net.andrewhatch.result.Result.failure;
import static net.andrewhatch.result.Result.success;
import static org.assertj.core.api.Assertions.assertThat;

public class ResultTest {
  @Test
  void eitherTest() {
    final String result = success("foo")
        .either(
            s -> "success",
            f -> "failure"
        );

    assertThat(result).isEqualTo("success");
  }

  @Test
  void successResultEquality() {
    final Result<String, String> foo = success("foo");
    assertThat(foo).isEqualTo(foo);

    assertThat(success("foo")).isEqualTo(success("foo"));
    assertThat(success("foo")).isNotEqualTo("foo");
  }

  @Test
  void failureResultEquality() {
    final Result<String, String> foo = failure("foo");
    assertThat(foo).isEqualTo(foo);

    assertThat(failure("foo")).isEqualTo(failure("foo"));
    assertThat(failure("foo")).isNotEqualTo("foo");
  }

  @Test
  void hashCodeTest() {
    final Set<Result<String, String>> set = new HashSet<>();
    set.add(success("foo"));
    set.add(success("foo"));
    set.add(failure("foo"));
    set.add(failure("foo"));

    assertThat(set.size()).isEqualTo(2);
  }
}
