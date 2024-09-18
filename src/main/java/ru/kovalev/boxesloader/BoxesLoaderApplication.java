package ru.kovalev.boxesloader;

import ru.kovalev.boxesloader.model.Truck;
import ru.kovalev.boxesloader.service.BoxesLoaderService;
import ru.kovalev.boxesloader.service.BoxesReaderService;
import ru.kovalev.boxesloader.service.TruckSpaceFinder;
import ru.kovalev.boxesloader.util.ConsoleHelper;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class BoxesLoaderApplication {
    public static void main(String[] args) {
        Path path = Path.of("src", "main", "resources", "init_boxes.txt");
        BoxesLoaderService loaderService = new BoxesLoaderService(new TruckSpaceFinder());
        BoxesReaderService boxesReaderService = new BoxesReaderService();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите размеры кузова:");
        int truckBodySize = scanner.nextInt();

        List<String> boxes = boxesReaderService.getBoxes(path);
        List<Truck> trucks = loaderService.distributeBoxes(boxes, truckBodySize);

        ConsoleHelper.printTrucks(trucks);
    }
}