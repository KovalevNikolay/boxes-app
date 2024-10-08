package ru.kovalev.boxesloader.io;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesapp.exception.FileReadingException;
import ru.kovalev.boxesapp.model.Truck;
import ru.kovalev.boxesapp.io.TruckInputOutput;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TruckInputOutputTest {
    private final TruckInputOutput truckInputOutput = new TruckInputOutput();

    @Test
    void whenFileIsEmptyListObjectsThenListIsEmpty() {
        Path path = Path.of("src", "test", "resources", "empty_list_objects_test.json");
        List<Truck> boxes = truckInputOutput.read(path);
        assertThat(boxes).isEmpty();
    }

    @Test
    void whenFileContainsObjectsThenListSizeEqualCountObjects() {
        Path path = Path.of("src", "test", "resources", "trucks_test.json");
        List<Truck> boxes = truckInputOutput.read(path);
        assertThat(boxes).hasSize(2);
    }

    @Test
    void whenFileDoesNotExist() {
        Path path = Path.of("");

        assertThatThrownBy(() -> truckInputOutput.read(path))
                .isInstanceOf(FileReadingException.class)
                .hasMessageContaining("Произошла ошибка при чтении файла с грузовиками: ");
    }
}
