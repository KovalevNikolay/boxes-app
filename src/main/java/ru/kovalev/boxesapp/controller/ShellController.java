package ru.kovalev.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.nio.file.Path;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {

    private final ProxyController operationProxy;

    @ShellMethod("посмотреть все посылки")
    public String boxes() {
        return operationProxy.boxes();
    }

    @ShellMethod("посмотреть посылку по названию")
    public String box(@ShellOption(value = "--name") String name) {
        return operationProxy.box(name);
    }

    @ShellMethod("удалить посылку")
    public String deleteBox(@ShellOption(value = "--name") String name) {
        return operationProxy.deleteBox(name);
    }

    @ShellMethod("добавить посылку")
    public String addBox(@ShellOption(value = "--name") String name,
                         @ShellOption(value = "--body") String body,
                         @ShellOption(value = "--marker") String marker
    ) {
        return operationProxy.addBox(name, body, marker);
    }

    @ShellMethod("обновить посылку")
    public String updateBox(@ShellOption(value = "--name") String name,
                            @ShellOption(value = "--body") String body,
                            @ShellOption(value = "--marker") String marker
    ) {
        return operationProxy.updateBox(name, body, marker);
    }

    @ShellMethod("погрузить посылки")
    public String loadBoxes(@ShellOption(value = "--boxes") String boxes,
                            @ShellOption(value = "--trucks") String trucks,
                            @ShellOption(value = "--strategy", defaultValue = "UNIFORM") String strategy
    ) {
        return operationProxy.loadBoxes(boxes, trucks, strategy);
    }

    @ShellMethod("погрузить посылки из файла")
    public String loadBoxesFromFile(@ShellOption(value = "--path") String path,
                                    @ShellOption(value = "--trucks") String trucks,
                                    @ShellOption(value = "--strategy", defaultValue = "UNIFORM") String strategy

    ) {
        return operationProxy.loadBoxesFromFile(path, trucks, strategy);
    }

    public String truckAnalyze(@ShellOption(value = "--path") String path) {
        return operationProxy.truckAnalyze(Path.of(path));
    }
}
