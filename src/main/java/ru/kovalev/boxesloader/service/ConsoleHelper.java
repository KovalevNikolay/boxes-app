package ru.kovalev.boxesloader.service;

import ru.kovalev.boxesloader.model.Truck;

import java.util.List;
import java.util.Map;

public class ConsoleHelper {

    /**
     * Выводит на консоль список грузовиков
     *
     * @param trucks список грузовиков для вывода на консоль
     */
    public void printTrucks(List<Truck> trucks) {
        for (Truck truck : trucks) {
            System.out.println(truck);
            System.out.println();
        }
    }

    /**
     * Выводит в консоль информацию о грузовике и количестве посылок в нем
     *
     * @param truck грузовик для которого выводится информацию
     * @param boxesInTruck - map, где ключ - тип посылки, значение - количество посылок данного типа
     */
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
