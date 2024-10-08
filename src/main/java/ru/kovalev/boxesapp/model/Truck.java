package ru.kovalev.boxesapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Truck {
    @Getter
    private final List<List<String>> body;
    @Getter
    @Setter
    private int loadCapacity;
    private String view;

    /**
     * Конструктор для создания грузовика с заданной высотой и длиной кузова
     *
     * @param height высота кузова
     * @param length длина кузова
     */
    public Truck(int height, int length) {
        body = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            body.add(Arrays.asList(new String[length]));
        }
        loadCapacity = 0;
    }

    /**
     * Конструктор для создания грузовика из JSON объекта. Используется для десериализации
     *
     * @param body двумерный массив - кузов грузовика
     */
    @JsonCreator
    public Truck(@JsonProperty("body") List<List<String>> body) {
        this.body = body;
        loadCapacity = 0;
    }

    /**
     * Возвращает строковое представление грузовика в виде отображения кузова и его содержимого
     *
     * @return строковое представление грузовика
     */
    @Override
    public String toString() {
        if (view == null) {
            view = calculateView();
        }
        return view;
    }

    private String calculateView() {
        StringBuilder stringBuilder = new StringBuilder();

        for (List<String> row : body) {
            stringBuilder.append("+");
            for (String cell : row) {
                stringBuilder.append(Objects.isNull(cell) ? " " : cell);
            }
            stringBuilder.append("+\n");
        }

        stringBuilder.append("+".repeat(Math.max(0, body.size() + 2)));

        return stringBuilder.toString();
    }
}
