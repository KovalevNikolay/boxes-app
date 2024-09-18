package ru.kovalev.boxesloader.util;

import ru.kovalev.boxesloader.model.Truck;

import java.util.List;

public final class ConsoleHelper {

    private ConsoleHelper() {
    }

    public static void printTrucks(List<Truck> trucks) {
        for (Truck truck : trucks) {
            System.out.println(truck);
            System.out.println();
        }
    }
}
