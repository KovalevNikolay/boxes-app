package ru.kovalev.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.kovalev.boxesapp.io.BoxesInputOutput;
import ru.kovalev.boxesapp.mapper.BoxesMapper;
import ru.kovalev.boxesapp.mapper.TrucksMapper;
import ru.kovalev.boxesapp.dto.BoxDto;
import ru.kovalev.boxesapp.dto.LoaderStrategy;
import ru.kovalev.boxesapp.dto.Truck;
import ru.kovalev.boxesapp.printer.TruckListPrinter;
import ru.kovalev.boxesapp.service.BoxesLoaderService;

import java.nio.file.Path;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoxesLoadController {

    private final BoxesLoaderService boxesLoaderService;
    private final BoxesMapper boxesMapper;
    private final TrucksMapper trucksMapper;
    private final BoxesInputOutput boxesInputOutput;
    private final TruckListPrinter truckListPrinter;

    public String loadBoxes(String boxes, String trucks, String strategy) {
        List<BoxDto> boxesList = boxesMapper.mapToList(boxes);
        return truckListPrinter.print(load(boxesList, trucks, strategy));
    }

    public String loadBoxesFromFile(String boxesPath, String trucks, String strategy) {
        List<BoxDto> boxesList = boxesInputOutput.read(Path.of(boxesPath));
        return truckListPrinter.print(load(boxesList, trucks, strategy));
    }

    private List<Truck> load(List<BoxDto> boxDtos, String trucks, String strategy) {
        List<Truck> truckList = trucksMapper.mapToList(trucks);
        LoaderStrategy loaderStrategy = LoaderStrategy.valueOf(strategy);
        return boxesLoaderService.load(boxDtos, truckList, loaderStrategy);
    }
}
