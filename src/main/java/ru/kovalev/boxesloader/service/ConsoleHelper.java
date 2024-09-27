package ru.kovalev.boxesloader.service;

import ru.kovalev.boxesloader.model.Truck;

import java.util.List;
import java.util.Map;

public class ConsoleHelper {

    public void printTrucks(List<Truck> trucks) {
        for (Truck truck : trucks) {
            System.out.println(truck);
            System.out.println();
        }
    }

    public void printBoxesInTruck(Truck truck, Map<Integer, Integer> boxesInTruck) {
        System.out.println("Грузовик: ");
        System.out.println(truck);
        System.out.println("\nКоличество посылок: ");
        for (Map.Entry<Integer, Integer> box : boxesInTruck.entrySet()) {
            System.out.println("Размер посылки: " + box.getKey() + " количество: " + box.getValue() + " шт.");
        }
        System.out.println();
    }
}
