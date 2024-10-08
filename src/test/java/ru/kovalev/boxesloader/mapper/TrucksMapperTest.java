package ru.kovalev.boxesloader.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import ru.kovalev.boxesapp.mapper.TrucksMapper;
import ru.kovalev.boxesapp.dto.Truck;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TrucksMapperTest {

    @InjectMocks
    private TrucksMapper trucksMapper;

    TrucksMapperTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenStringIsEmptyThenEmptyList() {
        String input = "";
        List<Truck> trucks = trucksMapper.mapToList(input);
        assertThat(trucks).isEmpty();
    }

    @Test
    void whenStringContainsTruckSizes() {
        String input = "5x5";
        List<Truck> trucks = trucksMapper.mapToList(input);
        assertThat(trucks).hasSize(1);
        List<List<String>> truckBody = trucks.getFirst().getBody();
        assertThat(truckBody).hasSize(5);
        assertThat(truckBody.getFirst()).hasSize(5);
    }

    @Test
    void whenTheBodySizesAreDifferent() {
        String input = "6x9";
        List<Truck> trucks = trucksMapper.mapToList(input);
        assertThat(trucks).hasSize(1);
        List<List<String>> truckBody = trucks.getFirst().getBody();
        assertThat(truckBody).hasSize(6);
        assertThat(truckBody.getFirst()).hasSize(9);
    }

    @Test
    void whenStringContainsSeveralTrucks() {
        String input = "3x4,5x6";
        List<Truck> trucks = trucksMapper.mapToList(input);
        assertThat(trucks).hasSize(2);
        List<List<String>> firstTruckBody = trucks.getFirst().getBody();
        assertThat(firstTruckBody).hasSize(3);
        assertThat(firstTruckBody.getFirst()).hasSize(4);

        List<List<String>> secondTruckBody = trucks.get(1).getBody();
        assertThat(secondTruckBody).hasSize(5);
        assertThat(secondTruckBody.getFirst()).hasSize(6);
    }
}
