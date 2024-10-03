package ru.kovalev.boxesapp.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.model.Box;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class BoxesWriter {
    public void write(List<Box> boxes, Path path) {
        try {
            new ObjectMapper().writeValue(new File(path.toUri()), boxes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
