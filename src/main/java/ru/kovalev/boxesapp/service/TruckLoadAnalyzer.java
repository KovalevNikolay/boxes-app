package ru.kovalev.boxesapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.io.TruckInputOutput;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.model.Truck;
import ru.kovalev.boxesapp.repository.BoxesRepository;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TruckLoadAnalyzer {

    private final BoxesRepository boxesRepository;
    private final TruckInputOutput truckInputOutput;

    public String getAnalyze(String path) {
        List<Truck> trucks = truckInputOutput.read(Path.of(path));
        StringBuilder result = new StringBuilder();
        for (Truck truck : trucks) {
            Map<Box, Integer> analyzeResult = analyze(truck);
            result.append(toString(truck, analyzeResult));
        }
        return result.toString();
    }

    private String toString(Truck truck, Map<Box, Integer> boxesInTruck) {
        StringBuilder resultAnalyze = new StringBuilder();
        resultAnalyze.append("Грузовик:\n").append(truck).append("Посылки:\n");
        for (Map.Entry<Box, Integer> box : boxesInTruck.entrySet()) {
            resultAnalyze.append(box.getKey().getName())
                    .append(" - ")
                    .append(box.getValue())
                    .append(" шт.");
        }
        return resultAnalyze.append("\n").toString();
    }

    /**
     * Анализирует загруженность грузовика и возвращает посылки, находящиеся в кузове
     *
     * @param truck грузовик, загруженность которого необходимо проанализировать
     * @return map, где ключ - тип посылки, значение - количество посылок данного типа
     */
    private Map<Box, Integer> analyze(Truck truck) {
        log.info("Начат анализ загруженности грузовика с параметрами: Высота = {}, Длина = {}",
                truck.getBody().size(), truck.getBody().getFirst().size());

        List<List<String>> body = truck.getBody();
        Map<Box, Integer> boxes = new HashMap<>();

        log.debug("Анализ содержимого кузова грузовика.");

        for (List<String> row : body) {
            for (String marker : row) {
                if (marker != null) {
                    Optional<Box> boxByMarker = boxesRepository.findByMarker(marker);
                    boxByMarker.ifPresentOrElse(
                            box -> updateBoxCount(box, boxes),
                            () -> log.error("Посылка с маркером \"{}\" не найдена.", marker)
                    );

                }
            }
        }

        log.info("Применение коррекции для вычисления количества посылок каждого типа.");
        boxes.replaceAll((box, allMarkers) -> allMarkers / box.getOccupiedSpace());

        log.info("Анализ загруженности грузовика завершен. Общее количество уникальных посылок: {}", boxes.size());
        return boxes;
    }

    private void updateBoxCount(Box box, Map<Box, Integer> boxes) {
        log.debug("Найдена посылка: {}", box.getName());
        Integer currentCount = boxes.getOrDefault(box, 0);
        boxes.put(box, ++currentCount);
    }
}
