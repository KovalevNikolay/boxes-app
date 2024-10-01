package ru.kovalev.boxesapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.kovalev.boxesapp.exception.FileReadingException;
import ru.kovalev.boxesapp.interfaces.JsonReader;
import ru.kovalev.boxesapp.model.Truck;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class TruckReader implements JsonReader<Truck> {

    /**
     * Читает список объектов типа {@link Truck} грузовик из JSON-файла
     *
     * @param path путь к JSON-файлу, из которого необходимо считать данные
     * @return список грузовиков считанных из файла
     * @throws FileReadingException если произошла ошибка при чтении файла
     */
    @Override
    public List<Truck> read(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(path), new TypeReference<>() {
            });
        } catch (IOException e) {
            log.error("Ошибка при чтении файла." + e.getMessage());
            throw new FileReadingException("Произошла ошибка при чтении файла: " + path, e);
        }
    }
}
