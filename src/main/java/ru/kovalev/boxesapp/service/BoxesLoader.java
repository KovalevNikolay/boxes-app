package ru.kovalev.boxesapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.kovalev.boxesapp.exception.BoxLoaderException;
import ru.kovalev.boxesapp.exception.OversizeBoxException;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.model.LoaderStrategy;
import ru.kovalev.boxesapp.model.Truck;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntBiFunction;

@Slf4j
@Service
public class BoxesLoader {
    private final BoxPlacementFinder placementFinder;

    /**
     * Конструктор
     *
     * @param placementFinder объект, используемый для нахождения места для посылки в грузовике
     */
    public BoxesLoader(BoxPlacementFinder placementFinder) {
        this.placementFinder = placementFinder;
    }


    public List<Truck> load(List<Box> boxes, List<Truck> trucks, LoaderStrategy loaderStrategy) {
        return switch (loaderStrategy) {
            case QUALITY -> loadBoxes(boxes, trucks, this::loadQuality);
            case UNIFORM -> loadBoxes(boxes, trucks, this::loadUniform);
        };
    }

    private List<Truck> loadBoxes(List<Box> boxes, List<Truck> trucks, ToIntBiFunction<List<Box>, List<Truck>> loadFunction) {

        if (CollectionUtils.isEmpty(boxes)) {
            log.warn("Список посылок пуст.");
            return Collections.emptyList();
        }

        if (CollectionUtils.isEmpty(trucks)) {
            log.warn("Список грузовиков пуст.");
            return Collections.emptyList();
        }

        log.info("Начало загрузки посылок. Количество посылок: {} шт., Количество грузовиков: {} шт.",
                boxes.size(), trucks.size());

        boxes.sort(Comparator.reverseOrder());
        log.debug("Посылки отсортированы по убыванию размера.");

        int countLoadedBoxes = loadFunction.applyAsInt(boxes, trucks);

        if (countLoadedBoxes != boxes.size()) {
            throw new BoxLoaderException("Ошибка распределения посылок. Количество посылок, которые не поместились: %d"
                                                 .formatted((boxes.size() - countLoadedBoxes)));
        }

        log.info("Загрузка успешно завершена.");
        return trucks;
    }

    private int loadQuality(List<Box> boxes, List<Truck> trucks) {
        log.info("Использование алгоритма качественной погрузки посылок.");

        int countLoadedBoxes = 0;

        for (Box box : boxes) {
            log.info("Попытка загрузки посылки с размерами: Высота = {}, Длина = {}", box.getHeight(), box.getLength());
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
            Truck truck = placementFinder.findTruckWithMinLoadCapacity(trucks);
            int currentLoadCapacity = truck.getLoadCapacity();
            if (loadToTruck(box, truck)) {
                truck.setLoadCapacity(currentLoadCapacity + box.getOccupiedSpace());
                log.info("Посылка успешно загружена в грузовик.");
                countLoadedBoxes++;
            }
        }
        return countLoadedBoxes;
    }


    private boolean loadToTruck(Box box, Truck truck) {
        List<List<String>> truckBody = truck.getBody();
        int boxHeight = box.getHeight();
        int boxLength = box.getLength();

        log.debug("Проверка размеров посылки и кузова грузовика: Посылка ({}x{}), Кузов ({}x{})",
                boxHeight, boxLength, truckBody.size(), truckBody.getFirst().size());

        if (boxHeight > truckBody.size() || boxLength > truckBody.getFirst().size()) {
            log.error("Размер посылки превышают размер кузова. Посылка: H={}, L={}, Кузов: H={}, L={}",
                    boxHeight, boxLength, truckBody.size(), truckBody.getFirst().size());
            throw new OversizeBoxException("Габариты посылки не могут превышать размеры кузова.");
        }

        int[] position = placementFinder.findPositionForBox(truckBody, boxHeight, boxLength);

        if (position.length == 0) {
            log.warn("Не удалось найти место для посылки в грузовике.");
            return false;
        }

        log.debug("Выполнятся погрузка посылки: H={}, L={}", boxHeight, boxLength);
        performLoading(box.getMarker(), truckBody, box.getBody(), position[0], position[1]);
        return true;
    }

    private void performLoading(String marker, List<List<String>> truckBody, List<List<String>> boxBody, int vertical, int left) {
        log.debug("Процесс загрузки посылки с маркером {} в грузовик.", marker);
        for (int i = boxBody.size() - 1; i >= 0; i--) {
            for (int j = 0; j < boxBody.get(i).size(); j++) {
                truckBody.get(vertical).set(left+j, marker);
            }
            vertical--;
        }
    }
}