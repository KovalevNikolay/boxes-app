package ru.kovalev.boxesloader.actions;

import ru.kovalev.boxesloader.interfaces.Action;
import ru.kovalev.boxesloader.interfaces.JsonReader;
import ru.kovalev.boxesloader.model.Box;
import ru.kovalev.boxesloader.model.Truck;
import ru.kovalev.boxesloader.service.BoxLoader;
import ru.kovalev.boxesloader.service.TruckGenerator;
import ru.kovalev.boxesloader.service.ConsoleHelper;

import java.util.List;
import java.util.Scanner;

public class LoadBoxesAction implements Action {
    private final BoxLoader loaderService;
    private final TruckGenerator truckGenerator;
    private final JsonReader<Box> boxReader;
    private final Scanner scanner;
    private final ConsoleHelper consoleHelper;

    public LoadBoxesAction(BoxLoader loaderService, TruckGenerator truckGenerator, JsonReader<Box> boxReader,
                           Scanner scanner, ConsoleHelper consoleHelper) {
        this.loaderService = loaderService;
        this.truckGenerator = truckGenerator;
        this.boxReader = boxReader;
        this.scanner = scanner;
        this.consoleHelper = consoleHelper;
    }

    @Override
    public void execute() {
        System.out.print("Введите размеры кузова грузовика. \nВведите высоту кузова: ");
        int height = scanner.nextInt();
        System.out.print("Введите длину кузова: ");
        int length = scanner.nextInt();
        System.out.print("Введите количество грузовиков: ");
        int countTrucks = scanner.nextInt();

        List<Truck> trucks = truckGenerator.generate(countTrucks, height, length);

        scanner.nextLine();

        System.out.print("Введите путь к JSON файлу с посылками: ");
        String path = scanner.nextLine();
        List<Box> boxes = boxReader.read(path);

        System.out.print("""
                Выберите алгоритм погрузки:
                1. Равномерная погрузка
                2. Качественная погрузка (плотная)
                (Введите цифру выбранного варианта)
                """);
        int option = scanner.nextInt();

        List<Truck> resultTrucks = switch (option) {
            case 1 -> loaderService.uniformLoading(boxes, trucks);
            case 2 -> loaderService.qualityLoading(boxes, trucks);
            default -> throw new IllegalArgumentException("Некорректный ввод: " + option);
        };

        consoleHelper.printTrucks(resultTrucks);
    }
}
