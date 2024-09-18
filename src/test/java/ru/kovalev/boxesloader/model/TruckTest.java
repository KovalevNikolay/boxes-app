package ru.kovalev.boxesloader.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TruckTest {
    @Test
    void bodyLengthEqualBodySize() {
        int bodySize = 6;
        Truck truck = new Truck(bodySize);
        String[][] body = truck.getBody();
        assertThat(body[0]).hasSize(bodySize);
    }
}
