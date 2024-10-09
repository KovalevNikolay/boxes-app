package ru.kovalev.boxesapp.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.exception.FileReadingException;
import ru.kovalev.boxesapp.exception.FileWritingException;
import ru.kovalev.boxesapp.dto.BoxDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Service
public class BoxesInputOutput {
    public List<BoxDto> read(Path path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(path.toUri()), new TypeReference<>() {});
        } catch (IOException e) {
            log.error("Произошла ошибка при чтении файла: {}", path, e);
            throw new FileReadingException("Произошла ошибка при чтении файла с посылками. ", e);
        }
    }

    public void write(List<BoxDto> boxes, Path path) {
        try {
            new ObjectMapper().writeValue(new File(path.toUri()), boxes);
        } catch (IOException e) {
            log.error("Произошла ошибка при записи файла: {}", path, e);
            throw new FileWritingException("Произошла ошибка при записи посылок в файл. ", e);
        }
    }
}
