package ru.kovalev.boxesapp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.interfaces.Mapper;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.repository.BoxesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoxesMapper implements Mapper<String, List<Box>> {
    private final BoxesRepository boxesRepository;
    @Override
    public List<Box> mapFrom(String input) {
        List<Box> boxes = new ArrayList<>();
        String[] boxNames = input.split(",");
        for (String boxName : boxNames) {
            Optional<Box> optionalBox = boxesRepository.findByName(boxName);
            optionalBox.ifPresent(boxes::add);
        }
        return boxes;
    }
}
