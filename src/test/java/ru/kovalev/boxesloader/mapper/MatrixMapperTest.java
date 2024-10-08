package ru.kovalev.boxesloader.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import ru.kovalev.boxesapp.mapper.MatrixMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MatrixMapperTest {

    @InjectMocks
    private MatrixMapper matrixMapper;

    MatrixMapperTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenStringIsEmptyThenEmptyList() {
        String input = "";
        List<List<String>> matrix = matrixMapper.mapToMatrix(input);
        assertThat(matrix).isEmpty();
    }

    @Test
    void whenStringIsOneDimensionalArray() {
        String input = "@,@,@";
        List<List<String>> matrix = matrixMapper.mapToMatrix(input);
        assertThat(matrix).hasSize(1);
        assertThat(matrix.getFirst()).hasSize(3);
    }

    @Test
    void whenStringIsTwoDimensionalArray() {
        String input = "@,@,@;@,@,@";
        List<List<String>> matrix = matrixMapper.mapToMatrix(input);
        assertThat(matrix).hasSize(2);
        assertThat(matrix.getFirst()).hasSize(3);
    }

    @Test
    void whenSizesOfTheSubArraysAreDifferent() {
        String input = "@,@,@;@,@,@,@;@,@";
        List<List<String>> matrix = matrixMapper.mapToMatrix(input);
        assertThat(matrix).hasSize(3);
        assertThat(matrix.get(0)).hasSize(3);
        assertThat(matrix.get(1)).hasSize(4);
        assertThat(matrix.get(2)).hasSize(2);
    }
}
