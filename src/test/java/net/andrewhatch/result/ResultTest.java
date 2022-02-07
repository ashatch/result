package net.andrewhatch.result;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import static net.andrewhatch.result.Result.failure;
import static net.andrewhatch.result.Result.success;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

public class ResultTest {

  @Test
  void eitherTest() {
    final String result = success("foo").either(s -> "success", f -> "failure");

    assertThat(result).isEqualTo("success");
  }

  @Test
  void successResultEquality() {
    final Result<String, String> foo = success("foo");
    assertThat(foo).isEqualTo(foo);

    assertThat(success("foo")).isEqualTo(success("foo"));
    assertThat(success("foo")).isNotEqualTo(null);
  }

  @Test
  void failureResultEquality() {
    final Result<String, String> foo = failure("foo");
    assertThat(foo).isEqualTo(foo);

    assertThat(failure("foo")).isEqualTo(failure("foo"));
    assertThat(failure("foo")).isNotEqualTo(null);
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

  @Test
  void thenSuccessTest() {
    success("foo")
        .then(r -> success(r.either(
            String::toUpperCase,
            Function.identity())))
        .apply(
            s -> assertThat(s).isEqualTo("FOO"),
            f -> fail("test fail"));
  }

  @Test
  void thenFailureTest() {
    failure("foo")
        .then(r -> failure(r.either(
            Function.identity(),
            String::toUpperCase)))
        .apply(
            s -> fail("test fail"),
            f -> assertThat(f).isEqualTo("FOO"));
  }

  @Test
  void applySuccessTest() {
    success("foo").apply(
        s -> assertThat(s).isEqualTo("foo"),
        f -> fail("test fail"));
  }

  @Test
  void applyFailureTest() {
    failure("foo").apply(
        s -> fail("test fail"),
        f -> assertThat(f).isEqualTo("foo"));
  }

  @Test
  void successWithTypeHint() {
    success("foo", Integer.class)
        .apply(
            s -> assertThat(s).isEqualTo("foo"),
            f -> fail("this integer addition should compile but "
                + "the test fails if we reach this line " + f + 1));
  }

  @Test
  void failureWithSuccessTypeHint() {
    failure("foo", Integer.class)
        .apply(
            s -> fail("this integer addition should compile but "
                + "the test fails if we reach this line " + s + 1),
            f -> assertThat(f).isEqualTo("foo"));
  }

  @Test
  void mapSuccess() {
    success("1")
        .map(Integer::parseInt)
        .apply(
            s -> assertThat(s).isEqualTo(1),
            f -> fail("fault")
        );
  }

  @Test
  void mapFail() {
    failure("1", String.class)
        .map(Integer::parseInt)
        .apply(
            s -> fail("fault"),
            f -> assertThat(f).isEqualTo("1")
        );
  }

  @Test
  void flatMapSuccess() {
    success("foo")
        .flatMap(Result::success)
        .apply(
            s -> assertThat(s).isEqualTo("foo"),
            f -> fail("fault")
        );
  }

  @Test
  void flatMapFail() {
    failure("1", String.class)
        .flatMap(Result::success)
        .apply(
            s -> fail("fault"),
            f -> assertThat(f).isEqualTo("1")
        );
  }
}
