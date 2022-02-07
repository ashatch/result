package net.andrewhatch.result;

import java.util.function.Consumer;
import java.util.function.Function;

public class Support {

  public static <T> Function<T, Void> consumeAndDrop(Consumer<T> successConsumer) {
    return success -> {
      successConsumer.accept(success);
      return null;
    };
  }
}
