package ru.kovalev.boxesapp.mapper;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatrixMapper {
    public List<List<String>> mapToMatrix(String input) {
        if (input == null || input.isEmpty()) {
            return Collections.emptyList();
        }

        String[] rows = input.split(";");
        List<List<String>> matrix = new ArrayList<>();

        for (String row : rows) {
            String[] elements = row.split(",");
            matrix.add(List.of(elements));
        }
        return matrix;
    }

    public String mapToString(List<List<String>> matrix) {
        return matrix.stream()
                .map(row -> String.join(",", row))
                .collect(Collectors.joining(";"));
    }
}
