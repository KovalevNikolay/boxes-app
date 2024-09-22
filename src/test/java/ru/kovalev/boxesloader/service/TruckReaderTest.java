package ru.kovalev.boxesloader.service;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesloader.exception.FileReadingException;
import ru.kovalev.boxesloader.interfaces.JsonReader;
import ru.kovalev.boxesloader.model.Truck;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TruckReaderTest {
    private final JsonReader<Truck> truckReader = new TruckReader();

    @Test
    void whenFileIsEmptyListObjectsThenListIsEmpty() {
        String path = "C:\\Users\\Kovalev\\IdeaProjects\\ConsoleBoxes\\src\\test\\resources\\empty_list_objects_test.json";
        List<Truck> boxes = truckReader.read(path);
        assertThat(boxes).isEmpty();
    }

    @Test
    void whenFileContainsObjectsThenListSizeEqualCountObjects() {
        String path = "C:\\Users\\Kovalev\\IdeaProjects\\ConsoleBoxes\\src\\test\\resources\\trucks_test.json";
        List<Truck> boxes = truckReader.read(path);
        assertThat(boxes).hasSize(2);
    }

    @Test
    void whenFileDoesNotExist() {
        String path = " ";

        assertThatThrownBy(() -> truckReader.read(path))
                .isInstanceOf(FileReadingException.class)
                .hasMessageContaining("Произошла ошибка при чтении файла: ");
    }
}
