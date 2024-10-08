package ru.kovalev.boxesloader.model;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesapp.model.Truck;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TruckTest {
    @Test
    void bodyLengthEqualBodySize() {
        int truckHeight = 6;
        int truckLength = 8;
        Truck truck = new Truck(truckHeight, truckLength);
        List<List<String>> body = truck.getBody();
        assertThat(body).hasSize(truckHeight);
        assertThat(body.getFirst()).hasSize(truckLength);
    }
}
