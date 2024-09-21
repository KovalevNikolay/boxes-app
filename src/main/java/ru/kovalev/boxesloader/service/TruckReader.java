package ru.kovalev.boxesloader.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.kovalev.boxesloader.exception.FileReadingException;
import ru.kovalev.boxesloader.model.Truck;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class TruckReader implements JsonReader<Truck> {
    @Override
    public List<Truck> read(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(path), new TypeReference<>() {});
        } catch (IOException e) {
            log.error("Ошибка при чтении файла." + e.getMessage());
            throw new FileReadingException("Произошла ошибка при чтении файла: " + path, e);
        }
    }
}
