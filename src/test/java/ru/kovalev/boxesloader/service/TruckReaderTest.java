package ru.kovalev.boxesloader.service;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesapp.exception.FileReadingException;
import ru.kovalev.boxesapp.model.Truck;
import ru.kovalev.boxesapp.io.TruckReader;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TruckReaderTest {
    private final TruckReader truckReader = new TruckReader();

    @Test
    void whenFileIsEmptyListObjectsThenListIsEmpty() {
        Path path = Path.of("src", "test", "resources", "empty_list_objects_test.json");
        List<Truck> boxes = truckReader.read(path);
        assertThat(boxes).isEmpty();
    }

    @Test
    void whenFileContainsObjectsThenListSizeEqualCountObjects() {
        Path path = Path.of("src", "test", "resources", "trucks_test.json");
        List<Truck> boxes = truckReader.read(path);
        assertThat(boxes).hasSize(2);
    }

    @Test
    void whenFileDoesNotExist() {
        Path path = Path.of("");

        assertThatThrownBy(() -> truckReader.read(path))
                .isInstanceOf(FileReadingException.class)
                .hasMessageContaining("Произошла ошибка при чтении файла с грузовиками: ");
    }
}
