package ru.kovalev.boxesloader.interfaces;

import java.util.List;

public interface JsonReader<T> {
    List<T> read(String path);
}