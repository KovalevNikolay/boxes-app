package ru.kovalev.boxesloader.validator;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesloader.exception.ValidationException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        assertDoesNotThrow(() -> DataValidator.isValidData(validData));
    }

    @Test
    public void testEmptyLineAtStart() {
        List<String> dataWithEmptyLineAtStart = Arrays.asList(
                "",
                "999",
                "999",
                "999"
        );

        ValidationException thrown = assertThrows(ValidationException.class, () -> DataValidator.isValidData(dataWithEmptyLineAtStart));
        assertEquals("Файл не может начинаться с пустой строки.", thrown.getMessage());
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

        ValidationException thrown = assertThrows(ValidationException.class, () -> DataValidator.isValidData(dataWithConsecutiveEmptyLines));
        assertEquals("Найдено более одной пустой строки идущей подряд.", thrown.getMessage());
    }

    @Test
    public void testNonDigitString() {
        List<String> dataWithNonDigitString = Arrays.asList(
                "999",
                "abc",
                "999"
        );

        ValidationException thrown = assertThrows(ValidationException.class, () -> DataValidator.isValidData(dataWithNonDigitString));
        assertEquals("Найдена строка, содержащая не только цифры: abc", thrown.getMessage());
    }

    @Test
    public void testEmptyData() {
        List<String> emptyData = List.of("");

        ValidationException thrown = assertThrows(ValidationException.class, () -> DataValidator.isValidData(emptyData));
        assertEquals("Файл не может начинаться с пустой строки.", thrown.getMessage());
    }
}
