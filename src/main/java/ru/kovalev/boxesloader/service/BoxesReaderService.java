package ru.kovalev.boxesloader.service;

import ru.kovalev.boxesloader.exception.FileReadingException;
import ru.kovalev.boxesloader.validator.DataValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BoxesReaderService {

    public List<String> getBoxes(Path path) {
        List<String> boxes = readBoxes(path);
        List<String> result = new ArrayList<>();

        for (String box : boxes) {
            String currentBox = box.substring(0, 1);

            result.add(currentBox);
        }

        result.sort(Comparator.reverseOrder());
        return result;
    }

    private List<String> readBoxes(Path path) {
        List<String> strings = readFile(path);

        DataValidator.validate(strings);

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

    private List<String> readFile(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new FileReadingException("Произошла ошибка при чтении файла: " + path, e);
        }
    }
}
