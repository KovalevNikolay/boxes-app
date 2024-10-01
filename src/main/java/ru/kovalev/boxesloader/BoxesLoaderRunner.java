package ru.kovalev.boxesloader;

import ru.kovalev.boxesloader.actions.CalculateBoxesAction;
import ru.kovalev.boxesloader.actions.LoadBoxesAction;
import ru.kovalev.boxesloader.controller.BoxLoaderController;
import ru.kovalev.boxesloader.interfaces.JsonReader;
import ru.kovalev.boxesloader.model.Box;
import ru.kovalev.boxesloader.model.Truck;
import ru.kovalev.boxesloader.service.BoxLoader;
import ru.kovalev.boxesloader.service.BoxPlacementFinder;
import ru.kovalev.boxesloader.service.BoxReader;
import ru.kovalev.boxesloader.service.ConsoleHelper;
import ru.kovalev.boxesloader.service.TruckGenerator;
import ru.kovalev.boxesloader.service.TruckLoadAnalyzer;
import ru.kovalev.boxesloader.service.TruckReader;

import java.util.Scanner;

public class BoxesLoaderRunner {
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