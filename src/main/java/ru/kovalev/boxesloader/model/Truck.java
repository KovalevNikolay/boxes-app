package ru.kovalev.boxesloader.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
public class Truck {
    private final Integer[][] body;
    @Setter
    private int loadCapacity;

    public Truck(int height, int length) {
        body = new Integer[height][length];
        loadCapacity = 0;
    }

    @JsonCreator
    public Truck(@JsonProperty("body") Integer[][] body) {
        this.body = body;
        loadCapacity = 0;
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
}
