package net.andrewhatch.result;

import java.util.function.Function;

public class Core {

  public static <SuccessT, FailureT, NewSuccessT, NewFailureT extends FailureT>
  Function<
      Result<SuccessT, FailureT>,
      Result<NewSuccessT, FailureT>>
  attempt(
      final Function<SuccessT, Result<NewSuccessT, NewFailureT>> f
  ) {
    return r -> r.either(
        f.andThen(onFailure(x -> x)),
        Result::failure);
  }

  public static <SuccessT, FailureT, NewSuccessT>
  Function<
      Result<SuccessT, FailureT>,
      Result<NewSuccessT, FailureT>>
  onSuccess(
      final Function<SuccessT, NewSuccessT> f
  ) {
    return attempt(f.andThen(Result::success));
  }

  public static <SuccessT, FailureT, NewFailureT>
  Function<
      Result<SuccessT, FailureT>,
      Result<SuccessT, NewFailureT>>
  onFailure(
      final Function<FailureT, NewFailureT> f
  ) {
    final Function<FailureT, Result<SuccessT, NewFailureT>> failFunc = f.andThen(Result::failure);

    return r -> r.either(
        Result::success,
        failFunc.andThen(onSuccess(s -> s)));
  }
}
