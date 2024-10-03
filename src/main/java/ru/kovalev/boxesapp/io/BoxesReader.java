package ru.kovalev.boxesapp.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.exception.FileReadingException;
import ru.kovalev.boxesapp.model.Box;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Service
public class BoxesReader {
    public List<Box> read(Path path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(path.toUri()), new TypeReference<>() {});
        } catch (IOException e) {
            log.error("Произошла ошибка при чтении файла: {}", path, e);
            throw new FileReadingException("Произошла ошибка при чтении файла с посылками: ", e);
        }
    }
}
