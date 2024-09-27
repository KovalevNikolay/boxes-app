package ru.kovalev.boxesloader.controller;

import lombok.extern.slf4j.Slf4j;
import ru.kovalev.boxesloader.interfaces.Action;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BoxLoaderController {
    private final Map<Integer, Action> actions;

    public BoxLoaderController() {
        this.actions = new HashMap<>();
    }

    public void addAction(int option, Action action) {
        actions.put(option, action);
        log.info("Добавлено действие для опции: {}", option);
    }

    public void executeAction(int option) {
        log.info("Попытка выполнить действие для опции: {}", option);
        Action action = actions.getOrDefault(option, () -> {
            log.warn("Некорректный ввод: {}.", option);
            throw new IllegalArgumentException("Некорректный ввод. " + option);
        });
        action.execute();
        log.info("Выполнено действие для опции: {}", option);
    }
}
