package net.andrewhatch.result;

import java.util.function.Function;

import static net.andrewhatch.result.Result.failure;
import static net.andrewhatch.result.Result.success;

/**
 * Result type error handling functions.
 */
public class ErrorHandling {
  public static <SuccessT, NewSuccessT, X extends Exception, NewFailureT>
  Function<SuccessT, Result<NewSuccessT, NewFailureT>> tryTo(
      final ThrowingFunction<SuccessT, NewSuccessT, X> f,
      final Function<Exception, NewFailureT> failureMapper
  ) {
    return v -> {
      try {
        return success(f.apply(v));
      } catch (Exception ex) {
        return failure(failureMapper.apply(ex));
      }
    };
  }

  public interface ThrowingFunction<InT, OutT, ExceptionT extends Exception> {
    OutT apply(InT value) throws ExceptionT;
  }
}
