package ru.kovalev.boxesapp.interfaces;

import java.util.List;

public interface JsonReader<T> {
    List<T> read(String path);
}