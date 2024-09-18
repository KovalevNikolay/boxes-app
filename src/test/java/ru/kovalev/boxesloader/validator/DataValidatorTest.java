package ru.kovalev.boxesloader.validator;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesloader.exception.ValidationException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DataValidatorTest {

    @Test
    public void testValidData() {
        List<String> validData = Arrays.asList(
                "999",
                "999",
                "999",
                "",
                "8888",
                "8888",
                "",
                "777",
                "7777",
                "",
                "8888",
                "8888"
        );

        assertThatCode(() -> DataValidator.validate(validData))
                .doesNotThrowAnyException();
    }

    @Test
    public void testEmptyLineAtStart() {
        List<String> dataWithEmptyLineAtStart = Arrays.asList(
                "",
                "999",
                "999",
                "999"
        );

        assertThatThrownBy(() -> DataValidator.validate(dataWithEmptyLineAtStart))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Файл не может начинаться с пустой строки.");
    }

    @Test
    public void testConsecutiveEmptyLines() {
        List<String> dataWithConsecutiveEmptyLines = Arrays.asList(
                "999",
                "",
                "999",
                "",
                "",
                "999"
        );

        assertThatThrownBy(() -> DataValidator.validate(dataWithConsecutiveEmptyLines))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Найдено более одной пустой строки идущей подряд.");
    }

    @Test
    public void testNonDigitString() {
        List<String> dataWithNonDigitString = Arrays.asList(
                "999",
                "abc",
                "999"
        );

        assertThatThrownBy(() -> DataValidator.validate(dataWithNonDigitString))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Найдена строка, содержащая не только цифры: abc");
    }

    @Test
    public void testEmptyData() {
        List<String> emptyData = List.of("");

        assertThatThrownBy(() -> DataValidator.validate(emptyData))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Файл не может начинаться с пустой строки.");

    }
}
