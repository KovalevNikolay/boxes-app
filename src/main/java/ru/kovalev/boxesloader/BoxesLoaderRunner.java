package ru.kovalev.boxesloader;

import ru.kovalev.boxesloader.model.Box;
import ru.kovalev.boxesloader.model.Truck;
import ru.kovalev.boxesloader.service.*;
import ru.kovalev.boxesloader.util.ConsoleHelper;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BoxesLoaderRunner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("""
                Выберите дальнейшее действие:
                1. Погрузить посылки
                2. Посчитать количество посылок в машине
                (Введите цифру выбранного варианта)
                """);

        int option = scanner.nextInt();

        switch (option) {
            case 1 -> loadBoxes(scanner);
            case 2 -> calculateQuantityBoxesInTruck(scanner);
            default -> System.out.println("Некорректный ввод: " + option);
        }
    }

    private static void loadBoxes(Scanner scanner) {
        BoxLoader loaderService = new BoxLoader(new TruckSpaceFinder());
        JsonReader<Box> boxReader = new BoxReader();

        System.out.print("Введите размеры кузова грузовика. \nВведите высоту кузова: ");
        int height = scanner.nextInt();
        System.out.print("Введите длину кузова: ");
        int length = scanner.nextInt();

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

        List<Truck> trucks = switch (option) {
            case 1 -> uniformLoading(scanner, loaderService, boxes, height, length);
            case 2 -> loaderService.distributeBoxes(boxes, height, length);
            default -> throw new IllegalArgumentException("Некорректный ввод: " + option);
        };

        ConsoleHelper.printTrucks(trucks);
    }

    private static List<Truck> uniformLoading(Scanner scanner, BoxLoader loaderService, List<Box> boxes,
                                              int height,
                                              int length) {

        System.out.print("Введите количество посылок в каждом грузовике: ");
        int countBoxesInTruck = scanner.nextInt();
        return loaderService.uniformLoadingBoxes(boxes, height, length, countBoxesInTruck);
    }

    private static void calculateQuantityBoxesInTruck(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Введите путь к JSON файлу с грузовиками: ");
        String path = scanner.nextLine();

        JsonReader<Truck> truckReader = new TruckReader();
        TruckLoadAnalyzer truckLoadAnalyzer = new TruckLoadAnalyzer();
        List<Truck> trucks = truckReader.read(path);

        for (Truck truck : trucks) {
            Map<Integer, Integer> countBoxes = truckLoadAnalyzer.countBoxesInTruck(truck);
            ConsoleHelper.printBoxesInTruck(truck, countBoxes);
        }
    }
}