package ru.kovalev.boxesloader.util;

import ru.kovalev.boxesloader.exception.FileReadingException;
import ru.kovalev.boxesloader.validator.DataValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class BoxesManager {
    private static final Map<String, int[][]> BOX_SIZES = Map.of(
            "1", new int[1][1],
            "2", new int[1][2],
            "3", new int[1][3],
            "4", new int[1][4],
            "5", new int[1][5],
            "6", new int[2][3],
            "7", new int[][]{{0, 0, 0}, {0, 0, 0, 0}},
            "8", new int[2][4],
            "9", new int[3][3]
    );

    private BoxesManager() {
    }

    public static List<String> getBoxes(Path path) {
        List<String> boxes = readBoxes(path);
        List<String> result = new ArrayList<>();

        for (String box : boxes) {
            String currentBox = box.substring(0, 1);

            result.add(currentBox);
        }

        result.sort(Comparator.reverseOrder());
        return result;
    }

    private static List<String> readBoxes(Path path) {
        List<String> strings = readFile(path);
        boolean isValid = DataValidator.isValidData(strings);
        if (!isValid) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        int startIndex = 0;
        while (startIndex < strings.size()) {
            StringBuilder box = new StringBuilder();
            String current = strings.get(startIndex);
            if (!current.isEmpty()) {
                while (!current.isEmpty()) {
                    box.append(current);
                    startIndex++;
                    if (startIndex < strings.size()) {
                        current = strings.get(startIndex);
                    } else {
                        break;
                    }
                }
                result.add(box.toString());
                startIndex++;
            }
        }
        return result;
    }

    private static List<String> readFile(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new FileReadingException("Произошла ошибка при чтении файла: " + path, e);
        }
    }

    public static int[][] getBoxDimensions(String boxWeight) {
        return BOX_SIZES.get(boxWeight);
    }
}
