package ru.kovalev.boxesapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.kovalev.boxesapp.dto.BoxDto;
import ru.kovalev.boxesapp.dto.LoaderStrategy;
import ru.kovalev.boxesapp.dto.Truck;
import ru.kovalev.boxesapp.exception.BoxLoaderException;
import ru.kovalev.boxesapp.exception.OversizeBoxException;

import java.util.Collections;
import java.util.List;
import java.util.function.ToIntBiFunction;

@Slf4j
@Service
public class BoxesLoaderService {
    private final BoxPlacementFinder placementFinder;

    /**
     * Конструктор
     *
     * @param placementFinder объект, используемый для нахождения места для посылки в грузовике
     */
    public BoxesLoaderService(BoxPlacementFinder placementFinder) {
        this.placementFinder = placementFinder;
    }


    public List<Truck> load(List<BoxDto> boxDtos, List<Truck> trucks, LoaderStrategy loaderStrategy) {
        return switch (loaderStrategy) {
            case QUALITY -> loadBoxes(boxDtos, trucks, this::loadQuality);
            case UNIFORM -> loadBoxes(boxDtos, trucks, this::loadUniform);
        };
    }

    private List<Truck> loadBoxes(List<BoxDto> boxDtos, List<Truck> trucks, ToIntBiFunction<List<BoxDto>, List<Truck>> loadFunction) {

        if (CollectionUtils.isEmpty(boxDtos)) {
            log.warn("Список посылок пуст.");
            return Collections.emptyList();
        }

        if (CollectionUtils.isEmpty(trucks)) {
            log.warn("Список грузовиков пуст.");
            return Collections.emptyList();
        }

        log.info("Начало загрузки посылок. Количество посылок: {} шт., Количество грузовиков: {} шт.",
                boxDtos.size(), trucks.size());

        boxDtos.sort((box1, box2) -> Integer.compare(box2.getOccupiedSpace(), box1.getOccupiedSpace()));

        log.debug("Посылки отсортированы по убыванию размера.");

        int countLoadedBoxes = loadFunction.applyAsInt(boxDtos, trucks);

        if (countLoadedBoxes != boxDtos.size()) {
            throw new BoxLoaderException("Ошибка распределения посылок. Количество посылок, которые не поместились: %d"
                    .formatted((boxDtos.size() - countLoadedBoxes)));
        }

        log.info("Загрузка успешно завершена.");
        return trucks;
    }

    private int loadQuality(List<BoxDto> boxDtos, List<Truck> trucks) {
        log.info("Использование алгоритма качественной погрузки посылок.");

        int countLoadedBoxes = 0;

        for (BoxDto boxDto : boxDtos) {
            log.info("Попытка загрузки посылки с размерами: Высота = {}, Длина = {}", boxDto.getHeight(), boxDto.getLength());
            boolean isLoad;
            for (Truck truck : trucks) {
                isLoad = loadToTruck(boxDto, truck);
                if (isLoad) {
                    log.info("Посылка успешно загружена в грузовик.");
                    countLoadedBoxes++;
                    break;
                }
            }
        }

        return countLoadedBoxes;
    }

    private int loadUniform(List<BoxDto> boxDtos, List<Truck> trucks) {
        log.info("Использование алгоритма равномерной погрузки посылок.");
        int countLoadedBoxes = 0;

        for (BoxDto boxDto : boxDtos) {
            Truck truck = placementFinder.findTruckWithMinLoadCapacity(trucks);
            int currentLoadCapacity = truck.getLoadCapacity();
            if (loadToTruck(boxDto, truck)) {
                truck.setLoadCapacity(currentLoadCapacity + boxDto.getOccupiedSpace());
                log.info("Посылка успешно загружена в грузовик.");
                countLoadedBoxes++;
            }
        }
        return countLoadedBoxes;
    }


    private boolean loadToTruck(BoxDto boxDto, Truck truck) {
        List<List<String>> truckBody = truck.getBody();
        int boxHeight = boxDto.getHeight();
        int boxLength = boxDto.getLength();

        log.debug("Проверка размеров посылки и кузова грузовика: Посылка ({}x{}), Кузов ({}x{})",
                boxHeight, boxLength, truckBody.size(), truckBody.get(0).size());

        if (boxHeight > truckBody.size() || boxLength > truckBody.get(0).size()) {
            log.error("Размер посылки превышают размер кузова. Посылка: H={}, L={}, Кузов: H={}, L={}",
                    boxHeight, boxLength, truckBody.size(), truckBody.get(0).size());
            throw new OversizeBoxException("Габариты посылки не могут превышать размеры кузова.");
        }

        int[] position = placementFinder.findPositionForBox(truckBody, boxHeight, boxLength);

        if (position.length == 0) {
            log.warn("Не удалось найти место для посылки в грузовике.");
            return false;
        }

        log.debug("Выполнятся погрузка посылки: H={}, L={}", boxHeight, boxLength);
        performLoading(boxDto.getMarker(), truckBody, boxDto.getBody(), position[0], position[1]);
        return true;
    }

    private void performLoading(String marker, List<List<String>> truckBody, List<List<String>> boxBody, int vertical, int left) {
        log.debug("Процесс загрузки посылки с маркером {} в грузовик.", marker);
        for (int i = boxBody.size() - 1; i >= 0; i--) {
            for (int j = 0; j < boxBody.get(i).size(); j++) {
                truckBody.get(vertical).set(left + j, marker);
            }
            vertical--;
        }
    }
}