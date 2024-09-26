package ru.kovalev.boxesloader.interfaces;

import ru.kovalev.boxesloader.model.Box;
import ru.kovalev.boxesloader.model.Truck;

import java.util.List;

@FunctionalInterface
public interface LoaderStrategy {
    int load(List<Box> boxes, List<Truck> trucks);
}
