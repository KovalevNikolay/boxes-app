package ru.kovalev.boxesloader.util;

import ru.kovalev.boxesloader.validator.DataValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class BoxesManager {
    private static final Map<String, int[][]> SIZE_BOXES = Map.of(
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

    public static Map<String, Integer> getBoxes(Path path) {
        List<String> boxes = readBoxes(path);
        Map<String, Integer> result = new TreeMap<>(Comparator.reverseOrder());

        for (String box : boxes) {
            String currentBox = box.substring(0, 1);

            if (result.containsKey(currentBox)) {
                Integer count = result.get(currentBox);
                result.put(currentBox, ++count);
            } else {
                result.put(currentBox, 1);
            }
        }
        return result;
    }

    private static List<String> readBoxes(Path path) {
        try {
            List<String> strings = Files.readAllLines(path);
            boolean isValid = DataValidator.isValidData(strings);
            if (isValid) {
                List<String> result = new ArrayList<>();
                int index = 0;
                while (index < strings.size()) {
                    StringBuilder box = new StringBuilder();
                    String current = strings.get(index);
                    if (!current.isEmpty()) {
                        while (!current.isEmpty()) {
                            box.append(current);
                            index++;
                            if (index < strings.size()) {
                                current = strings.get(index);
                            } else {
                                break;
                            }
                        }
                        result.add(box.toString());
                        index++;
                    }
                }
                return result;
            } else {
                return Collections.emptyList();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int[][] getBoxDimensions(String boxWeight) {
        return SIZE_BOXES.get(boxWeight);
    }
}
