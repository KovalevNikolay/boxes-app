package ru.kovalev.boxesapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.dto.BoxDto;
import ru.kovalev.boxesapp.entity.Box;
import ru.kovalev.boxesapp.mapper.BoxesMapper;
import ru.kovalev.boxesapp.mapper.MatrixMapper;
import ru.kovalev.boxesapp.repository.BoxesRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoxesService {

    private final BoxesRepository boxesRepository;
    private final BoxesMapper boxesMapper;
    private final MatrixMapper matrixMapper;

    public List<BoxDto> getAll() {
        return boxesRepository.findAll().stream()
                .map(boxesMapper::mapFrom)
                .toList();
    }

    public Optional<BoxDto> getByName(String name) {
        return boxesRepository.findByName(name)
                .map(boxesMapper::mapFrom);
    }

    @Transactional
    public boolean delete(String name) {
        return boxesRepository.deleteByName(name) > 0;
    }

    public Box add(BoxDto boxDto) {
        return boxesRepository.save(boxesMapper.mapFrom(boxDto));
    }

    @Transactional
    public boolean update(BoxDto boxDto) {
        Box box = boxesRepository.findByName(boxDto.getName()).orElse(null);
        if (box != null) {
            box.setBody(matrixMapper.mapToString(boxDto.getBody()));
            box.setMarker(boxDto.getMarker());
            boxesRepository.save(box);
            return true;
        }
        return false;
    }

    public Optional<BoxDto> findByMarker(String marker) {
        return boxesRepository.findByMarker(marker)
                .map(boxesMapper::mapFrom);
    }
}