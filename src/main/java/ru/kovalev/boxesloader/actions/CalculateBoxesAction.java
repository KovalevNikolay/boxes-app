package ru.kovalev.boxesloader.actions;

import ru.kovalev.boxesloader.interfaces.Action;
import ru.kovalev.boxesloader.interfaces.JsonReader;
import ru.kovalev.boxesloader.model.Truck;
import ru.kovalev.boxesloader.service.TruckLoadAnalyzer;
import ru.kovalev.boxesloader.service.ConsoleHelper;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CalculateBoxesAction implements Action {
    private final JsonReader<Truck> truckReader;
    private final TruckLoadAnalyzer truckLoadAnalyzer;
    private final Scanner scanner;
    private final ConsoleHelper consoleHelper;

    public CalculateBoxesAction(JsonReader<Truck> truckReader, TruckLoadAnalyzer truckLoadAnalyzer, Scanner scanner,
                                ConsoleHelper consoleHelper) {
        this.truckReader = truckReader;
        this.truckLoadAnalyzer = truckLoadAnalyzer;
        this.scanner = scanner;
        this.consoleHelper = consoleHelper;
    }

    @Override
    public void execute() {
        scanner.nextLine();
        System.out.print("Введите путь к JSON файлу с грузовиками: ");
        String path = scanner.nextLine();

        List<Truck> trucks = truckReader.read(path);

        for (Truck truck : trucks) {
            Map<Integer, Integer> countBoxes = truckLoadAnalyzer.getCountBoxesInTruck(truck);
            consoleHelper.printBoxesInTruck(truck, countBoxes);
        }
    }
}
