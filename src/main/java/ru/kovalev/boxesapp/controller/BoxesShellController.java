package ru.kovalev.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.nio.file.Path;

@ShellComponent
@RequiredArgsConstructor
public class BoxesShellController {

    private final ProxyController proxyController;

    @ShellMethod("посмотреть все посылки")
    public String boxes() {
        return proxyController.boxes();
    }

    @ShellMethod("посмотреть посылку по названию")
    public String box(@ShellOption(value = "--name") String name) {
        return proxyController.box(name);
    }

    @ShellMethod("удалить посылку")
    public String deleteBox(@ShellOption(value = "--name") String name) {
        return proxyController.deleteBox(name);
    }

    @ShellMethod("добавить посылку")
    public String addBox(@ShellOption(value = "--name") String name,
                         @ShellOption(value = "--body") String body,
                         @ShellOption(value = "--marker") String marker
    ) {
        return proxyController.addBox(name, body, marker);
    }

    @ShellMethod("обновить посылку")
    public String updateBox(@ShellOption(value = "--name") String name,
                            @ShellOption(value = "--body") String body,
                            @ShellOption(value = "--marker") String marker
    ) {
        return proxyController.updateBox(name, body, marker);
    }

    @ShellMethod("погрузить посылки")
    public String loadBoxes(@ShellOption(value = "--boxes") String boxes,
                            @ShellOption(value = "--trucks") String trucks,
                            @ShellOption(value = "--strategy", defaultValue = "UNIFORM") String strategy
    ) {
        return proxyController.loadBoxes(boxes, trucks, strategy);
    }

    @ShellMethod("погрузить посылки из файла")
    public String loadBoxesFromFile(@ShellOption(value = "--path") String path,
                                    @ShellOption(value = "--trucks") String trucks,
                                    @ShellOption(value = "--strategy", defaultValue = "UNIFORM") String strategy

    ) {
        return proxyController.loadBoxesFromFile(path, trucks, strategy);
    }

    public String truckAnalyze(@ShellOption(value = "--path") String path) {
        return proxyController.truckAnalyze(Path.of(path));
    }
}
