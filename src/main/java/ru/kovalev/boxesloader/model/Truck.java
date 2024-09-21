package ru.kovalev.boxesloader.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Truck {
    private final Integer[][] body;

    public Truck(int height, int length) {
        body = new Integer[height][length];
    }

    @JsonCreator
    public Truck(@JsonProperty("body") Integer[][] body) {
        this.body = body;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Integer[] row : body) {
            stringBuilder.append("+");
            for (Integer cell : row) {
                stringBuilder.append(Objects.isNull(cell) ? " " : cell);
            }
            stringBuilder.append("+\n");
        }

        stringBuilder.append("+".repeat(Math.max(0, body.length + 2)));

        return stringBuilder.toString();
    }

    public Integer[][] getBody() {
        return this.body;
    }
}
