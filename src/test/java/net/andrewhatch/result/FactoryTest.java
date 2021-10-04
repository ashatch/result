package net.andrewhatch.result;

import java.util.Optional;
import org.junit.jupiter.api.Test;

import static net.andrewhatch.result.Factory.fromOptional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class FactoryTest {
  @Test
  void fromOptionalTest() {
    fromOptional(Optional.of("foo"), () -> "not found")
        .either(
            s -> assertThat(s).isEqualTo("foo"),
            f -> fail("failed")
        );

    fromOptional(Optional.empty(), () -> "not found")
        .either(
            s -> fail("failed"),
            f -> assertThat(f).isEqualTo("not found")
        );
  }
}
