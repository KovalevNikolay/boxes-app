package ru.kovalev.boxesloader.validator;

import ru.kovalev.boxesloader.exception.ValidationException;

import java.util.List;
import java.util.regex.Pattern;

public class DataValidator {
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d+");

    private DataValidator() {
    }
    public static void validate(List<String> strings) {
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

            if (!DIGIT_PATTERN.matcher(string).matches()) {
                throw new ValidationException("Найдена строка, содержащая не только цифры: " + string);
            }

            isPreviousEmpty = false;
        }
    }
}
