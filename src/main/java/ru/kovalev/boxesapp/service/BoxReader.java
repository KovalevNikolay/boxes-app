package ru.kovalev.boxesapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.kovalev.boxesapp.exception.FileReadingException;
import ru.kovalev.boxesapp.interfaces.JsonReader;
import ru.kovalev.boxesapp.model.Box;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class BoxReader implements JsonReader<Box> {

    /**
     * Читает список объектов типа {@link Box} из JSON-файла по указанному пути.
     *
     * @param path путь к JSON-файлу
     * @return список объектов типа {@link Box}, считанных из файла
     * @throws FileReadingException если возникает ошибка при чтении файла
     */
    @Override
    public List<Box> read(String path) {
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
