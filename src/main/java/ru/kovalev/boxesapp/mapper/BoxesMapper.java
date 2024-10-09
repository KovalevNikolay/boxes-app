package ru.kovalev.boxesapp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.dto.BoxDto;
import ru.kovalev.boxesapp.entity.Box;
import ru.kovalev.boxesapp.repository.BoxesRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoxesMapper {

    private final BoxesRepository boxesRepository;
    private final MatrixMapper matrixMapper;

    public List<BoxDto> mapToList(String boxNames) {
        return Arrays.stream(boxNames.split(","))
                .map(boxesRepository::findByName)
                .flatMap(Optional::stream)
                .map(this::mapFrom)
                .toList();

    }

    public Box mapFrom(BoxDto boxDto) {
        return Box.builder()
                .name(boxDto.getName())
                .body(matrixMapper.mapToString(boxDto.getBody()))
                .marker(boxDto.getMarker())
                .build();
    }

    public BoxDto mapFrom(Box box) {
        return new BoxDto(
                box.getName(),
                matrixMapper.mapToMatrix(box.getBody()),
                box.getMarker()
        );
    }
}
