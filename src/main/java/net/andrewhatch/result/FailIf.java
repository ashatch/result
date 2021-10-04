package net.andrewhatch.result;

import java.util.function.Function;
import java.util.function.Predicate;

import static net.andrewhatch.result.Result.failure;
import static net.andrewhatch.result.Result.success;

public class FailIf {
  public static <SuccessT, FailureT>
  Function<SuccessT, Result<SuccessT, FailureT>> failIfFalse(
      final Predicate<SuccessT> test,
      final FailureT failValue
  ) {
    return r -> test.test(r) ? success(r) : failure(failValue);
  }

  public static <SuccessT, NewSuccessT, FailureT>
  Function<SuccessT, Result<NewSuccessT, FailureT>> failIfNull(
      final Function<SuccessT, NewSuccessT> test,
      final FailureT failValue
  ) {
    return r -> {
      final NewSuccessT value = test.apply(r);
      return value == null ? failure(failValue) : success(value);
    };
  }
}
