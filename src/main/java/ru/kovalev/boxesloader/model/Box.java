package ru.kovalev.boxesloader.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Box(int[][] sizes) implements Comparable<Box> {

    /**
     * Конструктор для создания посылки из JSON.
     * Используется для десериализации.
     *
     * @param sizes двумерный массив целых чисел, представляющий размеры посылки.
     */
    @JsonCreator
    public Box(@JsonProperty("sizes") int[][] sizes) {
        this.sizes = sizes;
    }

    /**
     * Возвращает маркер - символ, которым обозначается посылка
     *
     * @return маркер посылки
     */
    public Integer getMarker() {
        return sizes[0][0];
    }

    /**
     * Возвращает максимальную длину посылки
     * Актуально для посылки вида:
     * 777
     * 7777
     *
     * @return максимальная длина посылки
     */
    public int getMaxLength() {
        int maxLength = 0;
        for (int[] size : sizes) {
            maxLength = Math.max(size.length, maxLength);
        }
        return maxLength;
    }

    /**
     * Возвращает высоту посылки
     *
     * @return высота посылки
     */
    public int getHeight() {
        return sizes.length;
    }

    /**
     * Сравнивает две посылки по их маркеру
     *
     * @param o другая посылка
     * @return отрицательное значение, если текущая посылка меньше
     *         положительное значение, если текущая посылка больше
     *         ноль, если маркеры посылок равны
     */
    @Override
    public int compareTo(Box o) {
        return Integer.compare(this.sizes[0][0], o.sizes[0][0]);
    }
}
