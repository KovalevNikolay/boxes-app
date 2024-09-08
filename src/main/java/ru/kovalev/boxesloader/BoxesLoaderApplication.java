package ru.kovalev.boxesloader;

import ru.kovalev.boxesloader.model.Truck;
import ru.kovalev.boxesloader.service.BoxesLoaderService;
import ru.kovalev.boxesloader.util.BoxesManager;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class BoxesLoaderApplication {
    public static void main(String[] args) {
        Path path = Path.of("src", "main", "resources", "init_boxes.txt");
        Map<String, Integer> boxes = BoxesManager.getBoxes(path);
        BoxesLoaderService loaderService = BoxesLoaderService.getInstance();
        List<Truck> trucks = loaderService.distributeBoxes(boxes);
        loaderService.printTrucks(trucks);
    }
}