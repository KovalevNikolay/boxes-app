package ru.kovalev.boxesloader.service;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesloader.model.Truck;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TruckGeneratorTest {
    private final TruckGenerator truckGenerator = new TruckGenerator();

    @Test
    void whenCountTruckZeroThenEmptyList() {
        int countTruck = 0;
        List<Truck> trucks = truckGenerator.generate(countTruck, 5, 5);
        assertThat(trucks).isEmpty();
    }

    @Test
    void whenCountTruckNotZeroThenListSizeEqualCountTruck() {
        int countTruck = 5;
        List<Truck> trucks = truckGenerator.generate(countTruck, 3, 3);
        assertThat(trucks).hasSize(countTruck);
    }

    @Test
    void whenHeightLessOrEqualZeroThenException() {
        assertThatThrownBy(() -> truckGenerator.generate(1, 0, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Размеры кузова грузовика не могут быть меньше или равны нулю.");
    }

    @Test
    void whenLengthLessOrEqualZeroThenException() {
        assertThatThrownBy(() -> truckGenerator.generate(1, 2, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Размеры кузова грузовика не могут быть меньше или равны нулю.");
    }
}
