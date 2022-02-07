package net.andrewhatch.result;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static net.andrewhatch.result.Support.consumeAndDrop;

public interface Result<SuccessT, FailureT> extends Serializable {
  static <S, F> Result<S, F> success(
      final S value
  ) {
    return new Success<>(value);
  }

  static <S, F> Result<S, F> failure(
      final F value
  ) {
    return new Failure<>(value);
  }

  static <S, F> Result<S, F> success(
      final S value,
      final Class<F> failureClassType
  ) {
    return new Success<>(value);
  }

  static <S, F> Result<S, F> failure(
      final F value,
      final Class<S> successClassType
  ) {
    return new Failure<>(value);
  }

  <R, T extends R, U extends R> R either(
      final Function<SuccessT, T> onSuccess,
      final Function<FailureT, U> onFailure
  );

  default void apply(
      final Consumer<SuccessT> successConsumer,
      final Consumer<FailureT> failureConsumer
  ) {
    either(
        consumeAndDrop(successConsumer),
        consumeAndDrop(failureConsumer)
    );
  }

  default <T, U extends T> T then(
      final Function<Result<SuccessT, FailureT>, U> f
  ) {
    return f.apply(this);
  }

  default <U> Result<U, FailureT> map(Function<SuccessT, U> func) {
    return either(
        s -> success(func.apply(s)),
        Result::failure
    );
  }

  default <U> Result<U, FailureT> flatMap(Function<SuccessT, Result<U, FailureT>> func) {
    return either(
        func::apply,
        Result::failure
    );
  }

  final class Success<S, F> implements Result<S, F> {
    private static final long serialVersionUID = 1L;

    private final S value;

    private Success(final S value) {
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

  final class Failure<S, F> implements Result<S, F> {
    private static final long serialVersionUID = 1L;

    private final F value;

    private Failure(final F value) {
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
