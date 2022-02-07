package net.andrewhatch.result;

import java.util.Optional;
import java.util.function.Supplier;

import static net.andrewhatch.result.Result.failure;

public class Factory {
  public static <SuccessT, FailureT> Result<SuccessT, FailureT> fromOptional(
      final Optional<SuccessT> optional,
      final Supplier<FailureT> ifEmptyFailureSupplier
  ) {
    return optional.map(Result::<SuccessT, FailureT>success)
        .orElseGet(() -> failure(ifEmptyFailureSupplier.get()));
  }
}
