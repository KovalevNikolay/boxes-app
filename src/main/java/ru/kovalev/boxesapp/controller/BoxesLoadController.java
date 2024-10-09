package ru.kovalev.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.kovalev.boxesapp.dto.BoxDto;
import ru.kovalev.boxesapp.dto.LoaderStrategy;
import ru.kovalev.boxesapp.dto.Truck;
import ru.kovalev.boxesapp.io.BoxesReader;
import ru.kovalev.boxesapp.mapper.BoxesMapper;
import ru.kovalev.boxesapp.mapper.TrucksMapper;
import ru.kovalev.boxesapp.service.BoxesLoaderService;

import java.nio.file.Path;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoxesLoadController {

    private final BoxesLoaderService boxesLoaderService;
    private final BoxesMapper boxesMapper;
    private final TrucksMapper trucksMapper;
    private final BoxesReader boxesReader;

    public List<Truck> loadBoxes(String boxes, String trucks, String strategy) {
        List<BoxDto> boxesList = boxesMapper.mapToList(boxes);
        return load(boxesList, trucks, strategy);
    }

    public List<Truck> loadBoxesFromFile(String boxesPath, String trucks, String strategy) {
        List<BoxDto> boxesList = boxesReader.read(Path.of(boxesPath));
        return load(boxesList, trucks, strategy);
    }

    private List<Truck> load(List<BoxDto> boxDtos, String trucks, String strategy) {
        List<Truck> truckList = trucksMapper.mapToList(trucks);
        LoaderStrategy loaderStrategy = LoaderStrategy.valueOf(strategy);
        return boxesLoaderService.load(boxDtos, truckList, loaderStrategy);
    }
}
