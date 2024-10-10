package ru.kovalev.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.kovalev.boxesapp.dto.AnalyzeResult;
import ru.kovalev.boxesapp.dto.BoxDto;
import ru.kovalev.boxesapp.formatter.LoadResultFormatter;
import ru.kovalev.boxesapp.mapper.MatrixMapper;
import ru.kovalev.boxesapp.service.BoxesService;
import ru.kovalev.boxesapp.service.TruckLoadAnalyzer;

import java.nio.file.Path;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProxyController {

    private final BoxesService boxesService;
    private final LoadController loadController;
    private final LoadResultFormatter loadResultFormatter;
    private final TruckLoadAnalyzer truckLoadAnalyzer;
    private final MatrixMapper matrixMapper;

    public String boxes() {
        return boxesService.getAll().stream()
                .map(BoxDto::toString)
                .collect(Collectors.joining());
    }

    public String box(String name) {
        return boxesService.getByName(name)
                .map(BoxDto::toString)
                .orElse(String.format("Посылка с именем '%s' не найдена.", name));
    }

    public String deleteBox(String name) {
        return boxesService.delete(name)
                ? String.format("Посылка '%s' удалена.", name)
                : String.format("Посылка '%s' не найдена.", name);
    }

    public String addBox(String name, String body, String marker) {
        boxesService.add(new BoxDto(name, matrixMapper.mapToMatrix(body), marker));
        return String.format("Посылка '%s' добавлена.", name);
    }

    public String updateBox(String name, String body, String marker) {
        return boxesService.update(new BoxDto(name, matrixMapper.mapToMatrix(body), marker))
                ? String.format("Посылка '%s' обновлена.", name)
                : String.format("Посылки '%s' не существует.", name);
    }

    public String loadBoxes(String boxes, String trucks, String strategy) {
        return loadResultFormatter.format(loadController.loadBoxes(boxes, trucks, strategy.toUpperCase()));
    }

    public String loadBoxesFromFile(String path, String trucks, String strategy) {
        return loadResultFormatter.format(loadController.loadBoxesFromFile(path, trucks, strategy));
    }

    public String truckAnalyze(Path path) {
        return truckLoadAnalyzer.analyzeFromFile(path).stream()
                .map(AnalyzeResult::toString)
                .collect(Collectors.joining());
    }
}
