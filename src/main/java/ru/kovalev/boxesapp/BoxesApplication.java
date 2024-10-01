package ru.kovalev.boxesapp;

import ru.kovalev.boxesapp.action.CalculateBoxesAction;
import ru.kovalev.boxesapp.action.LoadBoxesAction;
import ru.kovalev.boxesapp.controller.BoxLoaderController;
import ru.kovalev.boxesapp.interfaces.JsonReader;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.model.Truck;
import ru.kovalev.boxesapp.service.BoxLoader;
import ru.kovalev.boxesapp.service.BoxPlacementFinder;
import ru.kovalev.boxesapp.service.BoxReader;
import ru.kovalev.boxesapp.service.ConsoleHelper;
import ru.kovalev.boxesapp.service.TruckGenerator;
import ru.kovalev.boxesapp.service.TruckLoadAnalyzer;
import ru.kovalev.boxesapp.service.TruckReader;

import java.util.Scanner;

public class BoxesApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BoxLoader boxLoader = new BoxLoader(new BoxPlacementFinder());
        TruckGenerator truckGenerator = new TruckGenerator();
        BoxLoaderController loaderController = getBoxLoaderController(boxLoader, truckGenerator, scanner);

        System.out.print("""
                Выберите дальнейшее действие:
                1. Погрузить посылки
                2. Посчитать количество посылок в машине
                (Введите цифру выбранного варианта)
                """);

        int option = scanner.nextInt();
        loaderController.executeAction(option);
    }

    private static BoxLoaderController getBoxLoaderController(BoxLoader boxLoader, TruckGenerator truckGenerator, Scanner scanner) {
        JsonReader<Box> boxReader = new BoxReader();
        JsonReader<Truck> truckReader = new TruckReader();
        TruckLoadAnalyzer truckLoadAnalyzer = new TruckLoadAnalyzer();
        ConsoleHelper consoleHelper = new ConsoleHelper();

        LoadBoxesAction loadBoxesAction = new LoadBoxesAction(boxLoader, truckGenerator, boxReader, scanner, consoleHelper);
        CalculateBoxesAction calculateBoxesAction = new CalculateBoxesAction(truckReader, truckLoadAnalyzer, scanner, consoleHelper);

        BoxLoaderController loaderController = new BoxLoaderController();
        loaderController.addAction(1, loadBoxesAction);
        loaderController.addAction(2, calculateBoxesAction);
        return loaderController;
    }
}