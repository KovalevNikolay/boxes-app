package ru.kovalev.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.kovalev.boxesapp.dto.BoxDto;
import ru.kovalev.boxesapp.dto.LoaderStrategy;
import ru.kovalev.boxesapp.dto.Truck;
import ru.kovalev.boxesapp.io.BoxesReader;
import ru.kovalev.boxesapp.mapper.BoxesMapper;
import ru.kovalev.boxesapp.mapper.TrucksMapper;
import ru.kovalev.boxesapp.service.LoaderService;

import java.nio.file.Path;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoadController {

    private final LoaderService loaderService;
    private final BoxesMapper boxesMapper;
    private final TrucksMapper trucksMapper;
    private final BoxesReader boxesReader;

    public List<Truck> loadBoxes(String boxes, String trucks, String strategy) {
        List<BoxDto> boxesList = boxesMapper.mapToList(boxes);
        return load(boxesList, trucks, strategy);
    }

    public List<Truck> loadBoxesFromFile(String boxes, String trucks, String strategy) {
        List<BoxDto> boxesList = boxesReader.read(Path.of(boxes));
        return load(boxesList, trucks, strategy);
    }

    public List<Truck> load(List<BoxDto> boxes, String trucks, String strategy) {
        List<Truck> truckList = trucksMapper.mapToList(trucks);
        LoaderStrategy loaderStrategy = LoaderStrategy.valueOf(strategy.toUpperCase());
        return loaderService.load(boxes, truckList, loaderStrategy);
    }
}