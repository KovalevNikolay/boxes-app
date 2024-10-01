package ru.kovalev.boxesapp.interfaces;

import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.model.Truck;

import java.util.List;

@FunctionalInterface
public interface LoaderStrategy {
    int load(List<Box> boxes, List<Truck> trucks);
}
