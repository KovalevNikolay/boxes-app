package ru.kovalev.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.kovalev.boxesapp.io.BoxesInputOutput;
import ru.kovalev.boxesapp.mapper.BoxesMapper;
import ru.kovalev.boxesapp.mapper.TruckMapper;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.model.LoaderStrategy;
import ru.kovalev.boxesapp.model.Truck;
import ru.kovalev.boxesapp.printer.TruckListPrinter;
import ru.kovalev.boxesapp.service.BoxesLoader;

import java.nio.file.Path;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoxesLoadController {
    private final BoxesLoader boxesLoader;
    private final BoxesMapper boxesMapper;
    private final TruckMapper truckMapper;
    private final BoxesInputOutput boxesInputOutput;
    private final TruckListPrinter truckListPrinter;

    public String loadBoxes(String boxes, String trucks, String strategy) {
        List<Box> boxesList = boxesMapper.mapToList(boxes);
        return truckListPrinter.print(load(boxesList, trucks, strategy));
    }

    public String loadBoxesFromFile(String boxesPath, String trucks, String strategy) {
        List<Box> boxesList = boxesInputOutput.read(Path.of(boxesPath));
        return truckListPrinter.print(load(boxesList, trucks, strategy));
    }

    private List<Truck> load(List<Box> boxes, String trucks, String strategy) {
        List<Truck> truckList = truckMapper.mapToList(trucks);
        LoaderStrategy loaderStrategy = LoaderStrategy.valueOf(strategy);
        return boxesLoader.load(boxes, truckList, loaderStrategy);
    }
}
