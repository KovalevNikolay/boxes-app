package ru.kovalev.boxesloader.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TruckTest {
    @Test
    void truckIsEmptyIfBoxNoAdd() {
        Truck truck = new Truck();
        assertThat(truck.isEmpty()).isTrue();
    }

    @Test
    void fieldsTruckBodyIsNotNull() {
        Truck truck = new Truck();
        String[][] body = truck.getBody();
        for (String[] strings : body) {
            assertThat(strings).isEqualTo(new String[] {"", "", "", "", "", ""});
        }
    }
}
