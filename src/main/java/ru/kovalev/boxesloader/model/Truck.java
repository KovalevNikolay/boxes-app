package ru.kovalev.boxesloader.model;

import java.util.Objects;

public class Truck {
    private final String[][] body;

    public Truck(int bodySize) {
        body = new String[bodySize][bodySize];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String[] strings : body) {
            stringBuilder.append("+");
            for (String string : strings) {
                stringBuilder.append(Objects.isNull(string) ? " " : string);
            }
            stringBuilder.append("+\n");
        }

        for (int i = 0; i < body.length + 2; i++) {
            stringBuilder.append("+");
        }

        return stringBuilder.toString();
    }

    public String[][] getBody() {
        return this.body;
    }
}
