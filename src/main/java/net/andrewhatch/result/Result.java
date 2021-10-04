package net.andrewhatch.result;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

public abstract class Result<SuccessT, FailureT> implements Serializable {
  private Result() {}

  public static <S, F> Result<S, F> success(
      final S value
  ) {
    return new Success<>(value);
  }

  public static <S, F> Result<S, F> failure(
      final F value
  ) {
    return new Failure<>(value);
  }

  public static <S, F> Result<S, F> success(
      final S value,
      final Class<F> __failureClassType
  ) {
    return new Success<>(value);
  }

  public static <S, F> Result<S, F> failure(
      final F value,
      final Class<S> __successClassType
  ) {
    return new Failure<>(value);
  }

  public abstract <R, T extends R, U extends R> R either(
      final Function<SuccessT, T> onSuccess,
      final Function<FailureT, U> onFailure
  );

  public <T, U extends T> T then(
      final Function<Result<SuccessT, FailureT>, U> biMapper
  ) {
    return biMapper.apply(this);
  }

  private static final class Success<S, F> extends Result<S, F> {
    private final S value;

    private Success(S value) {
      this.value = value;
    }

    @Override
    public <T, U extends T, V extends T> T either(
        final Function<S, U> onSuccess,
        final Function<F, V> onFailure
    ) {
      return onSuccess.apply(value);
    }

    @Override
    public boolean equals(final Object other) {
      if (this == other) {
        return true;
      }

      if (other == null || getClass() != other.getClass()) {
        return false;
      }

      final Success<?, ?> success = (Success<?, ?>) other;
      return Objects.equals(value, success.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value);
    }
  }

  private static final class Failure<S, F> extends Result<S, F> {
    private final F value;

    private Failure(F value) {
      this.value = value;
    }

    @Override
    public <T, U extends T, V extends T> T either(
        final Function<S, U> onSuccess,
        final Function<F, V> onFailure
    ) {
      return onFailure.apply(value);
    }

    @Override
    public boolean equals(final Object other) {
      if (this == other) {
        return true;
      }

      if (other == null || getClass() != other.getClass()) {
        return false;
      }

      final Failure<?, ?> success = (Failure<?, ?>) other;
      return Objects.equals(value, success.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value);
    }
  }
}
