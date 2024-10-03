package ru.kovalev.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.kovalev.boxesapp.mapper.MatrixMapper;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.repository.BoxesRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoxesController {

    private final BoxesRepository boxesRepository;
    private final MatrixMapper matrixMapper;

    public String getAll() {
        List<Box> boxes = boxesRepository.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Box box : boxes) {
            stringBuilder.append(box);
        }
        return stringBuilder.toString();
    }

    public String getByName(String name) {
        return boxesRepository.findByName(name)
                .map(Box::toString)
                .orElse("Посылка с именем '%s' не найдена.".formatted(name));
    }

    public String deleteBox(String name) {
        if (boxesRepository.deleteByName(name)) {
            return "Посылка '%s' удалена".formatted(name);
        }
        return "Посылка с именем '%s' не найдена.".formatted(name);
    }

    public String addBox(String name, String body, String marker) {
        if (boxesRepository.existsByName(name)) {
            return "Посылка с именем '%s' уже существует.".formatted(name);
        }
        boxesRepository.save(new Box(name, matrixMapper.mapFrom(body), marker));
        return "Посылка '%s' добавлена.".formatted(name);
    }

    public String updateBox(String name, String body, String marker) {
        return boxesRepository.update(new Box(name, matrixMapper.mapFrom(body), marker))
                ? "Посылки с именем '%s' не найдено.".formatted(name)
                : "Посылка '%s' успешно обновлена.".formatted(name);
    }

    public String saveAll() {
        boxesRepository.saveAll();
        return "Посылки успешно сохранены.";
    }
}
