package ru.kovalev.boxesloader.service;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesloader.exception.OversizedBoxException;
import ru.kovalev.boxesloader.model.Truck;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoxesLoaderServiceTest {
    private static final int TRUCK_BODY_SIZE = 6;
    private final BoxesLoaderService boxesLoaderService = new BoxesLoaderService(new TruckSpaceFinder());
    private List<String> boxes;

    @Test
    void whenBoxesIsEmptyThenTrucksIsEmpty() {
        List<Truck> trucks = boxesLoaderService.distributeBoxes(Collections.emptyList(), TRUCK_BODY_SIZE);
        assertThat(trucks).isEmpty();
    }

    @Test
    void whenBoxesIsNullThenTrucksIsEmpty() {
        List<Truck> trucks = boxesLoaderService.distributeBoxes(null, TRUCK_BODY_SIZE);
        assertThat(trucks).isEmpty();
    }

    @Test
    void boxesAreLoadedIntoOneTruck() {
        boxes = List.of("9", "6", "5", "1", "1", "3");
        List<Truck> trucks = boxesLoaderService.distributeBoxes(boxes, TRUCK_BODY_SIZE);
        assertThat(trucks).hasSize(1);
    }

    @Test
    void twoTrucksIfBoxDoesNotFit() {
        boxes = List.of("7", "9", "8");
        List<Truck> trucks = boxesLoaderService.distributeBoxes(boxes, TRUCK_BODY_SIZE);
        assertThat(trucks).hasSize(2);
    }

    @Test
    void whenFiveBoxesThenFiveTrucks() {
        boxes = List.of("1", "2", "3", "9", "2");
        List<Truck> trucks = boxesLoaderService.distributeHowOneTruckOneBox(boxes, TRUCK_BODY_SIZE);
        assertThat(trucks).hasSize(boxes.size());
    }

    @Test
    void whenSizeBoxesMoreThenSizeTruckBody() {
        boxes = List.of("9");
        assertThatThrownBy(() -> boxesLoaderService.distributeHowOneTruckOneBox(boxes, 1))
                .isInstanceOf(OversizedBoxException.class)
                .hasMessageContaining("Габариты посылки не могут превышать размеры кузова.");
    }
}
