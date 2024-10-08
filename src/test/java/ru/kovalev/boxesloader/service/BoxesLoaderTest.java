package ru.kovalev.boxesloader.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.model.LoaderStrategy;
import ru.kovalev.boxesapp.model.Truck;
import ru.kovalev.boxesapp.service.BoxesLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BoxesLoaderTest {

    @InjectMocks
    private BoxesLoader boxesLoader;

    BoxesLoaderTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenBoxesIsNullThenEmptyList() {
        List<Truck> resultQuality = boxesLoader.load(null, List.of(new Truck(1, 1)), LoaderStrategy.QUALITY);
        List<Truck> resultUniform = boxesLoader.load(null, List.of(new Truck(1, 1)), LoaderStrategy.UNIFORM);
        assertThat(resultQuality).isEmpty();
        assertThat(resultUniform).isEmpty();
    }

    @Test
    void whenBoxesIsEmptyThenEmptyList() {
        List<Truck> resultQuality = boxesLoader.load(List.of(), List.of(new Truck(1, 1)), LoaderStrategy.QUALITY);
        List<Truck> resultUniform = boxesLoader.load(List.of(), List.of(new Truck(1, 1)), LoaderStrategy.UNIFORM);
        assertThat(resultQuality).isEmpty();
        assertThat(resultUniform).isEmpty();
    }

    @Test
    void whenTruckIsNullThenEmptyList() {
        List<Truck> resultQuality = boxesLoader.load(List.of(new Box("", null, "")), null, LoaderStrategy.QUALITY);
        List<Truck> resultUniform = boxesLoader.load(List.of(new Box("", null, "")), null, LoaderStrategy.UNIFORM);
        assertThat(resultQuality).isEmpty();
        assertThat(resultUniform).isEmpty();
    }

    @Test
    void whenTruckIsEmptyThenEmptyList() {
        List<Truck> resultQuality = boxesLoader.load(List.of(new Box("", null, "")), List.of(), LoaderStrategy.QUALITY);
        List<Truck> resultUniform = boxesLoader.load(List.of(new Box("", null, "")), List.of(), LoaderStrategy.UNIFORM);
        assertThat(resultQuality).isEmpty();
        assertThat(resultUniform).isEmpty();
    }
}
