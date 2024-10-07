package ru.kovalev.boxesapp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.repository.BoxesRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoxesMapper {
    private final BoxesRepository boxesRepository;

    public List<Box> mapToList(String input) {
        return Arrays.stream(input.split(","))
                .map(boxesRepository::findByName)
                .flatMap(Optional::stream)
                .toList();

    }
}
