package ru.kovalev.boxesloader.service;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesloader.model.Truck;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TruckLoadAnalyzerTest {
    private final TruckLoadAnalyzer truckLoadAnalyzer = new TruckLoadAnalyzer();

    @Test
    void whenTruckBodyIsEmptyThenEmptyMap() {
        Truck truck = new Truck(new Integer[][]{
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
        });
        Map<Integer, Integer> boxesInTruck = truckLoadAnalyzer.getCountBoxesInTruck(truck);
        assertThat(boxesInTruck).isEmpty();
    }

    @Test
    void whenTruckContainsBoxes() {
        Truck truck = new Truck(new Integer[][]{
                {null, null, null, null},
                {2, 2, null, null},
                {2, 2, 1, null},
                {3, 3, 3, 1},
        });
        Map<Integer, Integer> boxesInTruck = truckLoadAnalyzer.getCountBoxesInTruck(truck);
        assertThat(boxesInTruck).hasSize(3);
        assertThat(boxesInTruck.get(3)).isEqualTo(1);
        assertThat(boxesInTruck.get(2)).isEqualTo(2);
        assertThat(boxesInTruck.get(1)).isEqualTo(2);
    }
}
