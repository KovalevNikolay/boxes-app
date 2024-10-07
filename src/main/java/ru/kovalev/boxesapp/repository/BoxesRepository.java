package ru.kovalev.boxesapp.repository;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.io.BoxesInputOutput;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BoxesRepository {
    private final Path path = Path.of("src", "main", "resources", "boxes.json");
    private final BoxesInputOutput boxesInputOutput;
    private Map<String, Box> boxes;

    @PostConstruct
    public void init() {
        boxes = boxesInputOutput.read(path).stream()
                .collect(Collectors.toMap(Box::getName, box -> box));
    }

    public List<Box> findAll() {
        return boxes.values().stream().toList();
    }

    public Optional<Box> findByName(String name) {
        return Optional.ofNullable(boxes.get(name));
    }

    public Optional<Box> findByMarker(String marker) {
        return boxes.values().stream()
                .filter(box -> marker.equals(box.getMarker()))
                .findFirst();
    }

    public boolean deleteByName(String name) {
        if (boxes.containsKey(name)) {
            boxes.remove(name);
            return true;
        }
        return false;
    }

    public boolean update(Box box) {
        if (boxes.containsKey(box.getName())) {
            boxes.put(box.getName(), box);
            return true;
        }
        return false;
    }

    public boolean save(Box box) {
        if (!boxes.containsKey(box.getName())) {
            boxes.put(box.getName(), box);
            return true;
        }
        return false;
    }

    public void saveAll() {
        boxesInputOutput.write(boxes.values().stream().toList(), path);
    }
}
