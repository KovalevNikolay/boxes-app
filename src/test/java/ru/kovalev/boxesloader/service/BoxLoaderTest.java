package ru.kovalev.boxesloader.service;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesloader.exception.OversizedBoxException;
import ru.kovalev.boxesloader.model.Box;
import ru.kovalev.boxesloader.model.Truck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoxLoaderTest {
    private static final int TRUCK_HEIGHT = 6;
    private static final int TRUCK_LENGTH = 6;
    private final BoxLoader boxLoader = new BoxLoader(new TruckSpaceFinder());
    private List<Box> boxes;

    @Test
    void whenBoxesIsEmptyThenTrucksIsEmpty() {
        List<Truck> trucks = boxLoader.distributeBoxes(Collections.emptyList(), TRUCK_HEIGHT, TRUCK_LENGTH);
        assertThat(trucks).isEmpty();
    }

    @Test
    void whenBoxesIsNullThenTrucksIsEmpty() {
        List<Truck> trucks = boxLoader.distributeBoxes(null, TRUCK_HEIGHT, TRUCK_LENGTH);
        assertThat(trucks).isEmpty();
    }

    @Test
    void boxesAreLoadedIntoOneTruck() {
        boxes = new ArrayList<>();
        boxes.add(new Box(new int[3][3]));
        boxes.add(new Box(new int[2][3]));
        boxes.add(new Box(new int[1][5]));
        boxes.add(new Box(new int[1][1]));
        boxes.add(new Box(new int[1][1]));
        boxes.add(new Box(new int[1][3]));

        List<Truck> trucks = boxLoader.distributeBoxes(boxes, TRUCK_HEIGHT, TRUCK_LENGTH);
        assertThat(trucks).hasSize(1);
    }

    @Test
    void twoTrucksIfBoxDoesNotFit() {
        boxes = new ArrayList<>();
        boxes.add(new Box(new int[][]{{7,7,7}, {7,7,7,7}}));
        boxes.add(new Box(new int[3][3]));
        boxes.add(new Box(new int[2][4]));

        List<Truck> trucks = boxLoader.distributeBoxes(boxes, TRUCK_HEIGHT, TRUCK_LENGTH);
        assertThat(trucks).hasSize(2);
    }

    @Test
    void whenFiveBoxesThenFiveTrucks() {
        boxes = new ArrayList<>();
        boxes.add(new Box(new int[1][1]));
        boxes.add(new Box(new int[1][2]));
        boxes.add(new Box(new int[1][3]));
        boxes.add(new Box(new int[3][3]));
        boxes.add(new Box(new int[1][2]));

        List<Truck> trucks = boxLoader.uniformLoadingBoxes(boxes, TRUCK_HEIGHT, TRUCK_LENGTH, 1);
        assertThat(trucks).hasSize(boxes.size());
    }

    @Test
    void whenSizeBoxesMoreThenSizeTruckBody() {
        boxes = List.of(new Box(new int[3][3]));
        assertThatThrownBy(() -> boxLoader.uniformLoadingBoxes(boxes, 1, 1, 1))
                .isInstanceOf(OversizedBoxException.class)
                .hasMessageContaining("Габариты посылки не могут превышать размеры кузова.");
    }
}
