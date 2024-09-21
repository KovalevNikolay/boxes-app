package ru.kovalev.boxesloader.service;

import lombok.extern.slf4j.Slf4j;
import ru.kovalev.boxesloader.exception.OversizedBoxException;
import ru.kovalev.boxesloader.model.Box;
import ru.kovalev.boxesloader.model.Truck;

import java.util.*;

@Slf4j
public class BoxLoader {

    private static final int ZERO_BOXES_IN_THE_TRUCK = 0;

    private final TruckSpaceFinder truckSpaceFinder;

    public BoxLoader(TruckSpaceFinder truckSpaceFinder) {
        this.truckSpaceFinder = truckSpaceFinder;
    }

    public List<Truck> distributeBoxes(List<Box> boxes, int truckHeight, int truckLength) {
        log.info("Начало распределения посылок. Количество посылок: {}, Высота грузовика: {}, Длина грузовика: {}",
                boxes.size(), truckHeight, truckLength);

        if (boxes == null || boxes.isEmpty()) {
            log.warn("Список посылок пуст.");
            return Collections.emptyList();
        }

        boxes.sort(Collections.reverseOrder());
        log.debug("Посылки отсортированы по убыванию размера.");

        List<Truck> trucks = new ArrayList<>();
        trucks.add(new Truck(truckHeight, truckLength));
        log.info("Создан первый грузовик.");

        for (Box box : boxes) {
            log.info("Попытка загрузки посылки с размерами: Высота = {}, Длина = {}", box.getHeight(), box.getMaxLength());
            boolean isLoad = false;
            for (Truck truck : trucks) {
                isLoad = loadToTruck(box, truck);
                if (isLoad) {
                    log.info("Посылка успешно загружена в грузовик.");
                    break;
                }
            }

            if (!isLoad) {
                log.info("Посылка не может быть загружена в текущие грузовики. Создание нового грузовика.");
                Truck truck = new Truck(truckHeight, truckLength);
                loadToTruck(box, truck);
                trucks.add(truck);
                log.info("Посылка загружена в новый грузовик.");
            }

        }
        log.info("Распределение посылок завершено. Использовано грузовиков: {}", trucks.size());
        return trucks;
    }

    public List<Truck> uniformLoadingBoxes(List<Box> boxes, int truckHeight, int truckLength, int countBoxesInTruck) {
        log.info("Начало равномерной загрузки посылок. Количество посылок: {}, Высота грузовика: {}, Длина грузовика: {}, Максимум посылок в грузовике: {}",
                boxes.size(), truckHeight, truckLength, countBoxesInTruck);

        if (boxes == null || boxes.isEmpty()) {
            log.warn("Список посылок пуст.");
            return Collections.emptyList();
        }

        Map<Truck, Integer> trucks = new HashMap<>();
        trucks.put(new Truck(truckHeight, truckLength), ZERO_BOXES_IN_THE_TRUCK);
        log.info("Создан первый грузовик для равномерной загрузки.");

        for (Box box : boxes) {
            log.info("Попытка загрузки посылки с размерами: Высота = {}, Длина = {}", box.getHeight(), box.getMaxLength());
            boolean isLoad = false;

            for (Map.Entry<Truck, Integer> truck : trucks.entrySet()) {
                Integer countLoadingBoxes = truck.getValue();
                if (countLoadingBoxes < countBoxesInTruck) {
                    Truck currentTruck = truck.getKey();
                    isLoad = loadToTruck(box, currentTruck);
                    if (isLoad) {
                        log.info("Посылка успешно загружена в грузовик.");
                        trucks.put(currentTruck, countLoadingBoxes + 1);
                        break;
                    }
                }
            }

            if (!isLoad) {
                log.info("Посылка не может быть загружена в текущие грузовики. Создание нового грузовика.");
                Truck truck = new Truck(truckHeight, truckLength);
                loadToTruck(box, truck);
                trucks.put(truck, ZERO_BOXES_IN_THE_TRUCK + 1);
                log.info("Посылка загружена в новый грузовик.");
            }
        }

        log.info("Равномерная загрузка посылок завершена. Использовано грузовиков: {}", trucks.size());
        return trucks.keySet().stream().toList();
    }

    private boolean loadToTruck(Box box, Truck truck) {
        Integer[][] truckBody = truck.getBody();
        int[][] boxSizes = box.sizes();
        int boxHeight = box.getHeight();
        int boxLength = box.getMaxLength();

        log.debug("Проверка размеров посылки и кузова грузовика: Посылка ({}x{}), Кузов ({}x{})",
                boxHeight, boxLength, truckBody.length, truckBody[0].length);

        if (boxHeight > truckBody.length || boxLength > truckBody[0].length) {
            log.error("Размер посылки превышают размер кузова. Посылка: H={}, L={}, Кузов: H={}, L={}",
                    boxHeight, boxLength, truckBody.length, truckBody[0].length);
            throw new OversizedBoxException("Габариты посылки не могут превышать размеры кузова.");
        }

        int[] position = truckSpaceFinder.findPositionForBox(truckBody, boxHeight, boxLength);

        if (position.length == 0) {
            log.warn("Не удалось найти место для посылки в грузовике.");
            return false;
        }

        log.debug("Выполнятся погрузка посылки: H={}, L={}", boxHeight, boxLength);
        performLoading(box.getMarker(), truckBody, boxSizes, position[0], position[1]);
        return true;
    }

    private void performLoading(Integer marker, Integer[][] truckBody, int[][] boxDimensions, int vertical, int left) {
        log.debug("Процесс загрузки посылки с маркером {} в грузовик.", marker);
        for (int i = boxDimensions.length - 1; i >= 0; i--) {
            for (int j = 0; j < boxDimensions[i].length; j++) {
                truckBody[vertical][left + j] = marker;
            }
            vertical--;
        }
    }
}