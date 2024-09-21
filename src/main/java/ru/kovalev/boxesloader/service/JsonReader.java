package ru.kovalev.boxesloader.service;

import java.util.List;

public interface JsonReader<T> {
    List<T> read(String path);
}
