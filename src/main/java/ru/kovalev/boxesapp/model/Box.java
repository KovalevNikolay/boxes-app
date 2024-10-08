package ru.kovalev.boxesapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Box implements Comparable<Box> {
    @Getter
    private final String name;
    @Getter
    @Setter
    private List<List<String>> body;
    @Getter
    @Setter
    private String marker;
    @JsonIgnore
    private Integer occupiedSpace;
    @JsonIgnore
    private String view;

    /**
     * Конструктор для создания посылки из JSON.
     * Используется для десериализации.
     *
     * @param body двумерный массив целых чисел, представляющий размеры посылки.
     */
    @JsonCreator
    public Box(@JsonProperty("name") String name, @JsonProperty("body") List<List<String>> body, @JsonProperty("marker") String marker) {
        this.name = name;
        this.body = body;
        this.marker = marker;
    }

    /**
     * Возвращает длину посылки
     *
     * @return максимальная длина посылки
     */
    @JsonIgnore
    public int getLength() {
        return body.getFirst().size();
    }

    /**
     * Возвращает высоту посылки
     *
     * @return высота посылки
     */
    @JsonIgnore
    public int getHeight() {
        return body.size();
    }

    /**
     * Количество ячеек, которое занимает посылка
     *
     * @return Количество ячеек, которое занимает посылка
     */

    public Integer getOccupiedSpace() {
        if (occupiedSpace == null) {
            occupiedSpace = calculateOccupiedSpace();
        }
        return occupiedSpace;
    }

    private int calculateOccupiedSpace() {
        int count = 0;
        for (List<String> row : body) {
            for (String cell : row) {
                if (cell.equals(marker)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        if (view == null) {
            view = calculateView();
        }
        return view;
    }

    private String calculateView() {
        StringBuilder bodyBuilder = new StringBuilder();

        for (List<String> row : body) {
            for (String element : row) {
                bodyBuilder.append(element == null ? " " : element).append(" ");
            }
            bodyBuilder.append("\n");
        }
        bodyBuilder.setLength(bodyBuilder.length() - 1);

        return """
                name: %s
                body:
                %s
                marker: '%s'
                
                """.formatted(name, bodyBuilder.toString(), marker);
    }

    /**
     * Сравнивает две посылки по их площади
     *
     * @param box другая посылка
     * @return отрицательное значение, если текущая посылка меньше
     * положительное значение, если текущая посылка больше
     * ноль, если маркеры посылок равны
     */
    @Override
    public int compareTo(Box box) {
        return Integer.compare(getOccupiedSpace(), box.getOccupiedSpace());
    }
}
