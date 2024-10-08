package ru.kovalev.boxesapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.dto.BoxDto;
import ru.kovalev.boxesapp.entity.Box;
import ru.kovalev.boxesapp.mapper.BoxesMapper;
import ru.kovalev.boxesapp.repository.BoxesRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoxesService {

    private final BoxesRepository boxesRepository;
    private final BoxesMapper boxesMapper;

    public List<BoxDto> getAll() {
        return boxesRepository.findAll().stream()
                .map(boxesMapper::mapFrom)
                .toList();
    }

    public Optional<BoxDto> getByName(String name) {
        return boxesRepository.findById(name)
                .map(boxesMapper::mapFrom);
    }

    public void delete(String name) {
        boxesRepository.deleteById(name);
    }

    public BoxDto add(String name, String body, String marker) {
        return boxesMapper.mapFrom(boxesRepository.save(new Box(name, body, marker)));
    }

    public boolean update(String name, String body, String marker) {
        return boxesRepository.update(new Box(name, body, marker));
    }

    public Optional<BoxDto> findByMarker(String marker) {
        return boxesRepository.findByMarker(marker)
                .map(boxesMapper::mapFrom);
    }
}
