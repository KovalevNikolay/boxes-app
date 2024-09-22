package ru.kovalev.boxesloader.service;

import lombok.extern.slf4j.Slf4j;
import ru.kovalev.boxesloader.exception.BoxLoaderException;
import ru.kovalev.boxesloader.exception.OversizedBoxException;
import ru.kovalev.boxesloader.interfaces.LoaderStrategy;
import ru.kovalev.boxesloader.model.Box;
import ru.kovalev.boxesloader.model.Truck;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class BoxLoader {
    private final TruckSpaceFinder truckSpaceFinder;

    public BoxLoader(TruckSpaceFinder truckSpaceFinder) {
        this.truckSpaceFinder = truckSpaceFinder;
    }

    public List<Truck> qualityLoading(List<Box> boxes, List<Truck> trucks) {
        return loadBoxes(boxes, trucks, this::loadQuality);
    }

    public List<Truck> uniformLoading(List<Box> boxes, List<Truck> trucks) {
        return loadBoxes(boxes, trucks, this::loadUniform);
    }

    private List<Truck> loadBoxes(List<Box> boxes, List<Truck> trucks, LoaderStrategy loaderStrategy) {
        log.info("Начало загрузки посылок. Количество посылок: {} шт., Количество грузовиков: {} шт.",
                boxes.size(), trucks.size());

        if (boxes == null || boxes.isEmpty()) {
            log.warn("Список посылок пуст.");
            return Collections.emptyList();
        }

        if (trucks == null || trucks.isEmpty()) {
            log.warn("Список грузовиков пуст.");
            return Collections.emptyList();
        }

        boxes.sort(Comparator.reverseOrder());
        log.debug("Посылки отсортированы по убыванию размера.");

        int countLoadedBoxes = loaderStrategy.load(boxes, trucks);

        if (countLoadedBoxes != boxes.size()) {
            throw new BoxLoaderException("Ошибка распределения посылок. Количество посылок, которые не поместились: "
                                         + (boxes.size() - countLoadedBoxes));
        }

        log.info("Загрузка успешно завершена.");
        return trucks;
    }

    private int loadQuality(List<Box> boxes, List<Truck> trucks) {
        log.info("Использование алгоритма качественной погрузки посылок.");

        int countLoadedBoxes = 0;

        for (Box box : boxes) {
            log.info("Попытка загрузки посылки с размерами: Высота = {}, Длина = {}", box.getHeight(), box.getMaxLength());
            boolean isLoad;
            for (Truck truck : trucks) {
                isLoad = loadToTruck(box, truck);
                if (isLoad) {
                    log.info("Посылка успешно загружена в грузовик.");
                    countLoadedBoxes++;
                    break;
                }
            }
        }

        return countLoadedBoxes;
    }

    private int loadUniform(List<Box> boxes, List<Truck> trucks) {
        log.info("Использование алгоритма равномерной погрузки посылок.");
        int countLoadedBoxes = 0;

        for (Box box : boxes) {
            Truck truck = truckSpaceFinder.findTruckWithMinLoadCapacity(trucks);
            int currentLoadCapacity = truck.getLoadCapacity();
            if (loadToTruck(box, truck)) {
                truck.setLoadCapacity(currentLoadCapacity + box.getMarker());
                log.info("Посылка успешно загружена в грузовик.");
                countLoadedBoxes++;
            }
        }
        return countLoadedBoxes;
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