package ru.kovalev.boxesapp.mapper;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MatrixMapper {
    public List<List<String>> mapToMatrix(String input) {
        if (StringUtils.isBlank(input)) {
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
}
