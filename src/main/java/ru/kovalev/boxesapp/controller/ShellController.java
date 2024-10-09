package ru.kovalev.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.kovalev.boxesapp.dto.BoxDto;
import ru.kovalev.boxesapp.service.BoxesService;
import ru.kovalev.boxesapp.service.TruckLoadAnalyzer;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {

    private final BoxesService boxesService;
    private final BoxesLoadController boxesLoadController;
    private final TruckLoadAnalyzer truckLoadAnalyzer;

    @ShellMethod("посмотреть все посылки")
    public String boxes() {
        return boxesService.getAll().stream()
                .map(BoxDto::toString)
                .collect(Collectors.joining());
    }

    @ShellMethod("посмотреть посылку по названию")
    public String box(@ShellOption(value = "--name") String name) {
        return boxesService.getByName(name)
                .map(BoxDto::toString)
                .orElse(String.format("Посылка с именем '%s' не найдена.", name));
    }

    @ShellMethod("удалить посылку")
    public void deleteBox(@ShellOption(value = "--name") String name) {
        boxesService.delete(name);
    }

    @ShellMethod("добавить посылку")
    public String addBox(@ShellOption(value = "--name") String name,
                         @ShellOption(value = "--body") String body,
                         @ShellOption(value = "--marker") String marker
    ) {
        return boxesService.add(name, body, marker) == null
                ? String.format("Посылка с именем '%s' уже существует.", name)
                : String.format("Посылка '%s' добавлена.", name);
    }

    @ShellMethod("обновить посылку")
    public String updateBox(@ShellOption(value = "--name") String name,
                            @ShellOption(value = "--body") String body,
                            @ShellOption(value = "--marker") String marker
    ) {
        return boxesService.update(name, body, marker) == null
                ? String.format("Посылки с именем '%s' не найдено.", name)
                : String.format("Посылка '%s' успешно обновлена.", name);
    }

    @ShellMethod("погрузить посылки")
    public String loadBoxes(@ShellOption(value = "--boxes") String boxes,
                            @ShellOption(value = "--trucks", defaultValue = "6x6,6x6,6x6") String trucks,
                            @ShellOption(value = "--strategy", defaultValue = "UNIFORM") String strategy
    ) {
        return boxesLoadController.loadBoxes(boxes, trucks, strategy);
    }

    @ShellMethod("погрузить посылки из файла")
    public String loadBoxesFromFile(@ShellOption(value = "--path") String boxesPath,
                                    @ShellOption(value = "--trucks", defaultValue = "6x6,6x6,6x6") String trucks,
                                    @ShellOption(value = "--strategy", defaultValue = "UNIFORM") String strategy

    ) {
        return boxesLoadController.loadBoxesFromFile(boxesPath, trucks, strategy);
    }

    public String truckAnalyze(@ShellOption(value = "--path") String path) {
        return truckLoadAnalyzer.getAnalyze(path);
    }
}
