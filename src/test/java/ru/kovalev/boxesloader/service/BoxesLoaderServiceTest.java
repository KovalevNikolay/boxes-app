package ru.kovalev.boxesloader.service;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesloader.model.Truck;
import ru.kovalev.boxesloader.util.BoxesManager;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BoxesLoaderServiceTest {
    private static final BoxesLoaderService boxesLoaderService = new BoxesLoaderService();
    private static final int TRUCK_BODY_SIZE = 6;
    private List<String> boxes;

    @Test
    void whenBoxesIsEmptyThenTrucksIsEmpty() {
        boxes = List.of();
        List<Truck> trucks = boxesLoaderService.distributeBoxes(boxes, TRUCK_BODY_SIZE);
        assertThat(trucks).isEmpty();
    }

    @Test
    void whenBoxesIsNullThenTrucksIsEmpty() {
        boxes = null;
        List<Truck> trucks = boxesLoaderService.distributeBoxes(boxes, TRUCK_BODY_SIZE);
        assertThat(trucks).isEmpty();
    }

    @Test
    void trucksEmptyIfInputFileEmpty() {
        Path path = Path.of("src", "test", "resources", "for_empty_trucks.txt");
        boxes = BoxesManager.getBoxes(path);
        List<Truck> trucks = boxesLoaderService.distributeBoxes(boxes, TRUCK_BODY_SIZE);
        assertThat(trucks).isEmpty();
    }

    @Test
    void boxesAreLoadedIntoOneTruck() {
        Path path = Path.of("src", "test", "resources", "for_one_trucks.txt");
        boxes = BoxesManager.getBoxes(path);
        List<Truck> trucks = boxesLoaderService.distributeBoxes(boxes, TRUCK_BODY_SIZE);
        assertThat(trucks).hasSize(1);
    }

    @Test
    void twoTrucksIfBoxDoesNotFit() {
        Path path = Path.of("src", "test", "resources", "for_two_trucks.txt");
        boxes = BoxesManager.getBoxes(path);
        List<Truck> trucks = boxesLoaderService.distributeBoxes(boxes, TRUCK_BODY_SIZE);
        assertThat(trucks).hasSize(2);
    }
}
