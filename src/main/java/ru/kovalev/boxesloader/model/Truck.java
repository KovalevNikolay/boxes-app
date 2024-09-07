package ru.kovalev.boxesloader.model;

import java.util.Arrays;

public class Truck {
    private static final int TRUCK_BODY_SIZE = 6;
    private final String[][] body;

    public Truck() {
        body = new String[TRUCK_BODY_SIZE][TRUCK_BODY_SIZE];
        initBody();
    }

    private void initBody() {
        for (String[] strings : body) {
            Arrays.fill(strings, "");
        }
    }

    public boolean isEmpty() {
        for (String[] strings : body) {
            for (String string : strings) {
                if (!string.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String[] strings : body) {
            stringBuilder.append("+");
            for (String string : strings) {
                stringBuilder.append(string.isEmpty() ? " " : string);
            }
            stringBuilder.append("+\n");
        }
        stringBuilder.append("++++++++");

        return stringBuilder.toString();
    }

    public String[][] getBody() {
        return this.body;
    }
}
