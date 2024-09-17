package ru.kovalev.boxesloader.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TruckTest {
    @Test
    void fieldsTruckBodyIsNotNull() {
        int bodySize = 6;
        Truck truck = new Truck(bodySize);
        String[][] body = truck.getBody();
        for (String[] strings : body) {
            assertThat(strings).isEqualTo(new String[bodySize]);
        }
    }
}
