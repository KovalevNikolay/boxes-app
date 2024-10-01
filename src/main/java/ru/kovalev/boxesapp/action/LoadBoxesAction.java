package ru.kovalev.boxesapp.action;

import ru.kovalev.boxesapp.interfaces.Action;
import ru.kovalev.boxesapp.interfaces.JsonReader;
import ru.kovalev.boxesapp.service.BoxLoader;
import ru.kovalev.boxesapp.service.ConsoleHelper;
import ru.kovalev.boxesapp.service.TruckGenerator;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.model.Truck;

import java.util.List;
import java.util.Scanner;

public class LoadBoxesAction implements Action {
    private final BoxLoader loaderService;
    private final TruckGenerator truckGenerator;
    private final JsonReader<Box> boxReader;
    private final Scanner scanner;
    private final ConsoleHelper consoleHelper;

    /**
     * Конструктор для инициализации действия загрузки посылок.
     *
     * @param loaderService  сервис погрузки посылок.
     * @param truckGenerator генератор грузовиков.
     * @param boxReader      сервис чтения посылок из JSON
     * @param scanner        объект для считывания пользовательского ввода.
     * @param consoleHelper  сервис для вывода информации в консоль.
     */
    public LoadBoxesAction(BoxLoader loaderService, TruckGenerator truckGenerator, JsonReader<Box> boxReader,
                           Scanner scanner, ConsoleHelper consoleHelper) {
        this.loaderService = loaderService;
        this.truckGenerator = truckGenerator;
        this.boxReader = boxReader;
        this.scanner = scanner;
        this.consoleHelper = consoleHelper;
    }

    /**
     * Выполняет действие по загрузке коробок в грузовики.
     * Запрашивает у пользователя размеры кузова, количество грузовиков и путь к JSON файлу с коробками,
     * а также алгоритм погрузки. Загружает коробки в грузовики и выводит результат.
     *
     * @throws IllegalArgumentException если введены некорректные данные, например, неверный номер алгоритма.
     */
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
                2. Качественная погрузка
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
