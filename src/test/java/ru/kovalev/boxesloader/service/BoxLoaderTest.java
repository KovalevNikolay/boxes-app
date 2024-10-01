package ru.kovalev.boxesloader.service;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesapp.exception.BoxLoaderException;
import ru.kovalev.boxesapp.exception.OversizedBoxException;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.model.Truck;
import ru.kovalev.boxesapp.service.BoxLoader;
import ru.kovalev.boxesapp.service.BoxPlacementFinder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoxLoaderTest {

    private final BoxLoader boxLoader = new BoxLoader(new BoxPlacementFinder());

    @Test
    void whenBoxSizeMoreThanTruckSize() {
        List<Box> boxes = new ArrayList<>();
        boxes.add(new Box(new int[3][3]));

        assertThatThrownBy(() -> boxLoader.qualityLoading(boxes, List.of(new Truck(2, 2))))
                .isInstanceOf(OversizedBoxException.class)
                .hasMessageContaining("Габариты посылки не могут превышать размеры кузова.");

        assertThatThrownBy(() -> boxLoader.uniformLoading(boxes, List.of(new Truck(2, 2))))
                .isInstanceOf(OversizedBoxException.class)
                .hasMessageContaining("Габариты посылки не могут превышать размеры кузова.");
    }

    @Test
    void whenCountBoxesIsToMuch() {
        List<Box> boxes = new ArrayList<>();
        boxes.add(new Box(new int[3][3]));
        boxes.add(new Box(new int[3][3]));
        boxes.add(new Box(new int[3][3]));
        boxes.add(new Box(new int[3][3]));
        boxes.add(new Box(new int[1][3]));
        boxes.add(new Box(new int[1][3]));

        assertThatThrownBy(() -> boxLoader.qualityLoading(boxes, List.of(new Truck(6, 6))))
                .isInstanceOf(BoxLoaderException.class)
                .hasMessageContaining("Ошибка распределения посылок. Количество посылок, которые не поместились: ");

        assertThatThrownBy(() -> boxLoader.uniformLoading(boxes, List.of(new Truck(6, 6))))
                .isInstanceOf(BoxLoaderException.class)
                .hasMessageContaining("Ошибка распределения посылок. Количество посылок, которые не поместились: ");
    }

    @Test
    void whenOptimalCountBoxesAndTrucks() {
        List<Box> boxes = new ArrayList<>();
        boxes.add(new Box(new int[3][3]));
        boxes.add(new Box(new int[3][3]));
        boxes.add(new Box(new int[1][3]));
        boxes.add(new Box(new int[1][3]));

        assertThatCode( () -> boxLoader.qualityLoading(boxes, List.of(new Truck(6, 6))))
                .doesNotThrowAnyException();

        assertThatCode( () -> boxLoader.uniformLoading(boxes, List.of(new Truck(6, 6))))
                .doesNotThrowAnyException();
    }
}