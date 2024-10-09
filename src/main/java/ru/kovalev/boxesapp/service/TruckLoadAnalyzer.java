package ru.kovalev.boxesapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.dto.AnalyzeResult;
import ru.kovalev.boxesapp.dto.BoxDto;
import ru.kovalev.boxesapp.dto.Truck;
import ru.kovalev.boxesapp.io.TruckReader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TruckLoadAnalyzer {

    private final BoxesService boxesService;
    private final TruckReader truckReader;

    public List<AnalyzeResult> analyze(Path path) {
        List<Truck> trucks = truckReader.read(path);
        if (trucks.isEmpty()) {
            return Collections.emptyList();
        }

        List<AnalyzeResult> analyzeResults = new ArrayList<>();
        for (Truck truck : trucks) {
            analyzeResults.add(analyze(truck));
        }
        return analyzeResults;
    }

    /**
     * Анализирует загруженность грузовика и возвращает посылки, находящиеся в кузове
     *
     * @param truck грузовик, загруженность которого необходимо проанализировать
     * @return map, где ключ - тип посылки, значение - количество посылок данного типа
     */
    private AnalyzeResult analyze(Truck truck) {
        log.info("Начат анализ загруженности грузовика с параметрами: Высота = {}, Длина = {}",
                truck.getBody().size(), truck.getBody().getFirst().size());

        List<List<String>> body = truck.getBody();
        Map<BoxDto, Integer> analyzeResult = new HashMap<>();

        log.debug("Анализ содержимого кузова грузовика.");

        for (List<String> row : body) {
            for (String marker : row) {
                if (marker != null) {
                    Optional<BoxDto> boxByMarker = boxesService.findByMarker(marker);
                    boxByMarker.ifPresentOrElse(
                            box -> updateBoxCount(box, analyzeResult),
                            () -> log.error("Посылка с маркером \"{}\" не найдена.", marker)
                    );

                }
            }
        }

        log.info("Применение коррекции для вычисления количества посылок каждого типа.");
        analyzeResult.replaceAll((box, allMarkers) -> allMarkers / box.getOccupiedSpace());

        log.info("Анализ загруженности грузовика завершен. Общее количество уникальных посылок: {}", analyzeResult.size());

        return new AnalyzeResult(analyzeResult, truck);
    }

    private void updateBoxCount(BoxDto boxDto, Map<BoxDto, Integer> boxes) {
        log.debug("Найдена посылка: {}", boxDto.getName());
        Integer currentCount = boxes.getOrDefault(boxDto, 0);
        boxes.put(boxDto, ++currentCount);
    }
}
