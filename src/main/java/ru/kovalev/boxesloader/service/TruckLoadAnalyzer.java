package ru.kovalev.boxesloader.service;

import lombok.extern.slf4j.Slf4j;
import ru.kovalev.boxesloader.model.Truck;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TruckLoadAnalyzer {
    public Map<Integer, Integer> getCountBoxesInTruck(Truck truck) {
        log.info("Начат анализ загруженности грузовика с параметрами: Высота = {}, Длина = {}",
                truck.getBody().length, truck.getBody()[0].length);

        Integer[][] body = truck.getBody();
        Map<Integer, Integer> boxCount = new HashMap<>();

        log.debug("Анализ содержимого кузова грузовика.");

        for (Integer[] row : body) {
            for (Integer cell : row) {
                if (cell != null) {
                    int currentCount = boxCount.getOrDefault(cell, 0);
                    boxCount.put(cell, currentCount + 1);

                    log.debug("Найдена посылка с маркером {}. Текущее количество для данного маркера: {}"
                            , cell, boxCount.get(cell));
                }
            }
        }

        log.info("Применение коррекции для вычисления количества посылок каждого размера.");
        boxCount.replaceAll((marker, countMarkers) -> countMarkers / marker);

        log.info("Анализ загруженности грузовика завершен. Общее количество уникальных посылок: {}", boxCount.size());
        return boxCount;
    }
}
