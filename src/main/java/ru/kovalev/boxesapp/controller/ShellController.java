package ru.kovalev.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {

    private final BoxesController boxesController;
    private final BoxesLoadController boxesLoadController;
    private final TruckBodyAnalyzeController truckBodyAnalyzeController;
    @ShellMethod("посмотреть все посылки")
    public String boxes() {
        return boxesController.getAll();
    }

    @ShellMethod("посмотреть посылку по названию")
    public String box(@ShellOption(value = "--name") String name) {
        return boxesController.getByName(name);
    }

    @ShellMethod("удалить посылку")
    public String deleteBox(@ShellOption(value = "--name") String name) {
        return boxesController.deleteBox(name);
    }

    @ShellMethod("добавить посылку")
    public String addBox(@ShellOption(value = "--name") String name,
                         @ShellOption(value = "--body") String body,
                         @ShellOption(value = "--marker") String marker
    ) {
        return boxesController.addBox(name, body, marker);
    }

    @ShellMethod("обновить посылку")
    public String updateBox(@ShellOption(value = "--name") String name,
                            @ShellOption(value = "--body") String body,
                            @ShellOption(value = "--marker") String marker
    ) {
        return boxesController.updateBox(name, body, marker);
    }

    @ShellMethod("сохранить посылки")
    public String saveAll() {
        return boxesController.saveAll();
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
        return truckBodyAnalyzeController.analyze(path);
    }
}
