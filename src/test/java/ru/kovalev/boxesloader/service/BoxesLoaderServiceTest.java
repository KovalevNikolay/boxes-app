package ru.kovalev.boxesloader.service;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesloader.model.Truck;
import ru.kovalev.boxesloader.util.BoxesManager;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BoxesLoaderServiceTest {
    private Map<String, Integer> boxes;
    private static final BoxesLoaderService boxesLoaderService = BoxesLoaderService.getInstance();

    @Test
    void trucksEmptyIfInputFileEmpty() {
        Path path = Path.of("src", "test", "resources", "empty.txt");
        boxes = BoxesManager.getBoxes(path);
        List<Truck> trucks = boxesLoaderService.distributeBoxesToTruck(boxes);
        assertThat(trucks).isEmpty();
    }

    @Test
    void boxesAreLoadedIntoOneTruck() {
        Path path = Path.of("src", "test", "resources", "one_trucks.txt");
        boxes = BoxesManager.getBoxes(path);
        List<Truck> trucks = boxesLoaderService.distributeBoxesToTruck(boxes);
        assertThat(trucks).hasSize(1);
    }

    @Test
    void twoTrucksIfBoxDoesNotFit() {
        Path path = Path.of("src", "test", "resources", "two_trucks.txt");
        boxes = BoxesManager.getBoxes(path);
        List<Truck> trucks = boxesLoaderService.distributeBoxesToTruck(boxes);
        assertThat(trucks).hasSize(2);
    }
}
