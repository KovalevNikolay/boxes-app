package ru.kovalev.boxesapp.mapper;

import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.interfaces.Mapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatrixMapper implements Mapper<String, List<List<String>>> {
    @Override
    public List<List<String>> mapFrom(String input) {
        String[] rows = input.split(";");
        List<List<String>> matrix = new ArrayList<>();

        for (String row : rows) {
            String[] elements = row.split(",");
            matrix.add(List.of(elements));
        }
        return matrix;
    }
}
