package ru.kovalev.boxesapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.mapper.MatrixMapper;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.repository.BoxesRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoxesService {

    private final BoxesRepository boxesRepository;
    private final MatrixMapper matrixMapper;

    public List<Box> getAll() {
        return boxesRepository.findAll();
    }

    public Optional<Box> getByName(String name) {
        return boxesRepository.findByName(name);
    }

    public boolean delete(String name) {
        return boxesRepository.deleteByName(name);
    }

    public boolean add(String name, String body, String marker) {
        return boxesRepository.save(new Box(name, matrixMapper.mapToMatrix(body), marker));
    }

    public boolean update(String name, String body, String marker) {
        return boxesRepository.update(new Box(name, matrixMapper.mapToMatrix(body), marker));
    }

    public void saveAll() {
        boxesRepository.saveAll();
    }
}
