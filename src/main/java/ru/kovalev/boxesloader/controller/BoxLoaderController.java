package ru.kovalev.boxesloader.controller;

import ru.kovalev.boxesloader.interfaces.Action;

import java.util.HashMap;
import java.util.Map;

public class BoxLoaderController {
    private final Map<Integer, Action> actions;

    public BoxLoaderController() {
        this.actions = new HashMap<>();
    }

    public void addAction(int option, Action action) {
        actions.put(option, action);
    }

    public void executeAction(int option) {
        Action action = actions.getOrDefault(option, () -> System.out.println("Некорректный ввод. " + option));
        action.execute();
    }
}
