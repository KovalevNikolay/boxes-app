package ru.kovalev.boxesloader.validator;

import ru.kovalev.boxesloader.exception.ValidationException;

import java.util.List;
import java.util.Map;

public class DataValidator {
    private static final String DIGIT_REGEX = "\\d+";

    private DataValidator() {
    }

    public static boolean isValidData(List<String> strings) {
        boolean isPreviousEmpty = false;
        for (String string : strings) {

            if (string.isEmpty()) {
                if (string.equals(strings.get(0))) {
                    throw new ValidationException("Файл не может начинаться с пустой строки.");
                }

                if (isPreviousEmpty) {
                    throw new ValidationException("Найдено более одной пустой строки идущей подряд.");
                }
                isPreviousEmpty = true;
                continue;
            }

            if (!string.matches(DIGIT_REGEX)) {
                throw new ValidationException("Найдена строка, содержащая не только цифры: " + string);
            }

            isPreviousEmpty = false;
        }
        return true;
    }
}
