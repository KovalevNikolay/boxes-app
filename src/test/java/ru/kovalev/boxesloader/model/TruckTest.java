package ru.kovalev.boxesloader.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TruckTest {
    @Test
    void bodyLengthEqualBodySize() {
        int truckHeight = 6;
        int truckLength = 8;
        Truck truck = new Truck(truckHeight, truckLength);
        Integer[][] body = truck.getBody();
        assertThat(body.length).isEqualTo(truckHeight);
        assertThat(body[0]).hasSize(truckLength);
    }
}
