package ru.kovalev.boxesloader.service;

import org.junit.jupiter.api.Test;
import ru.kovalev.boxesloader.exception.OversizedBoxException;
import ru.kovalev.boxesloader.model.Box;
import ru.kovalev.boxesloader.model.Truck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoxLoaderTest {
    private static final int TRUCK_HEIGHT = 6;
    private static final int TRUCK_LENGTH = 6;
    private final BoxLoader boxLoader = new BoxLoader(new TruckSpaceFinder());
    private List<Box> boxes;


}