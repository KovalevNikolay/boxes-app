package ru.kovalev.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.kovalev.boxesapp.dto.AnalyzeResult;
import ru.kovalev.boxesapp.dto.BoxDto;
import ru.kovalev.boxesapp.formatter.LoadResultFormatter;
import ru.kovalev.boxesapp.service.BoxesService;
import ru.kovalev.boxesapp.service.TruckLoadAnalyzer;

import java.nio.file.Path;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProxyController {

    private final BoxesService boxesService;
    private final BoxesLoadController boxesLoadController;
    private final LoadResultFormatter loadResultFormatter;
    private final TruckLoadAnalyzer truckLoadAnalyzer;

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
        boxesService.add(name, body, marker);
        return String.format("Посылка '%s' добавлена.", name);
    }

    public String updateBox(String name, String body, String marker) {
        return boxesService.update(name, body, marker)
                ? String.format("Посылка '%s' обновлена.", name)
                : String.format("Посылки '%s' не существует.", name);
    }

    public String loadBoxes(String boxes, String trucks, String strategy) {
        return loadResultFormatter.format(boxesLoadController.loadBoxes(boxes, trucks, strategy));
    }

    public String loadBoxesFromFile(String path, String trucks, String strategy) {
        return loadResultFormatter.format(boxesLoadController.loadBoxesFromFile(path, trucks, strategy));
    }

    public String truckAnalyze(Path path) {
        return truckLoadAnalyzer.analyze(path).stream()
                .map(AnalyzeResult::toString)
                .collect(Collectors.joining());
    }
}
