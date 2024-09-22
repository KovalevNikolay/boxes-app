package ru.kovalev.boxesloader.service;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesloader.exception.FileReadingException;
import ru.kovalev.boxesloader.interfaces.JsonReader;
import ru.kovalev.boxesloader.model.Box;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoxReaderTest {
    private final JsonReader<Box> boxReader = new BoxReader();

    @Test
    void whenFileIsEmptyListObjectsThenListIsEmpty() {
        String path = "C:\\Users\\Kovalev\\IdeaProjects\\ConsoleBoxes\\src\\test\\resources\\empty_list_objects_test.json";
        List<Box> boxes = boxReader.read(path);
        assertThat(boxes).isEmpty();
    }

    @Test
    void whenFileContainsObjectsThenListSizeEqualCountObjects() {
        String path = "C:\\Users\\Kovalev\\IdeaProjects\\ConsoleBoxes\\src\\test\\resources\\boxes_test.json";
        List<Box> boxes = boxReader.read(path);
        assertThat(boxes).hasSize(9);
    }

    @Test
    void whenFileDoesNotExist() {
        String path = " ";

        assertThatThrownBy(() -> boxReader.read(path))
                .isInstanceOf(FileReadingException.class)
                .hasMessageContaining("Произошла ошибка при чтении файла: ");
    }
}
