package ru.kovalev.boxesapp.action;

import ru.kovalev.boxesapp.interfaces.Action;
import ru.kovalev.boxesapp.interfaces.JsonReader;
import ru.kovalev.boxesapp.service.ConsoleHelper;
import ru.kovalev.boxesapp.service.TruckLoadAnalyzer;
import ru.kovalev.boxesapp.model.Truck;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CalculateBoxesAction implements Action {
    private final JsonReader<Truck> truckReader;
    private final TruckLoadAnalyzer truckLoadAnalyzer;
    private final Scanner scanner;
    private final ConsoleHelper consoleHelper;

    /**
     * Конструктор для инициализации действия анализа содержимого грузовиков.
     *
     * @param truckReader       сервис для чтения грузовиков из JSON.
     * @param truckLoadAnalyzer анализатор загрузки грузовиков.
     * @param scanner           объект для считывания пользовательского ввода.
     * @param consoleHelper     сервис для вывода информации в консоль.
     */
    public CalculateBoxesAction(JsonReader<Truck> truckReader, TruckLoadAnalyzer truckLoadAnalyzer, Scanner scanner,
                                ConsoleHelper consoleHelper) {
        this.truckReader = truckReader;
        this.truckLoadAnalyzer = truckLoadAnalyzer;
        this.scanner = scanner;
        this.consoleHelper = consoleHelper;
    }

    /**
     * Выполняет анализ содержимого грузовиков.
     * Запрашивает у пользователя путь к JSON файлу с грузовиками,
     * загружает данные и выводит количество посылок в каждом грузовике.
     */
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
