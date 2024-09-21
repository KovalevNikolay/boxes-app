package ru.kovalev.boxesloader.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Box(int[][] sizes) implements Comparable<Box> {
    @JsonCreator
    public Box(@JsonProperty("sizes") int[][] sizes) {
        this.sizes = sizes;
    }

    public Integer getMarker() {
        return sizes[0][0];
    }

    public int getMaxLength() {
        int maxLength = 0;
        for (int[] size : sizes) {
            maxLength = Math.max(size.length, maxLength);
        }
        return maxLength;
    }

    public int getHeight() {
        return sizes.length;
    }

    @Override
    public int compareTo(Box o) {
        return Integer.compare(this.sizes[0][0], o.sizes[0][0]);
    }
}
