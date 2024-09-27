package ru.kovalev.boxesloader.service;

import lombok.extern.slf4j.Slf4j;
import ru.kovalev.boxesloader.model.Truck;

import java.util.List;
import java.util.Objects;

@Slf4j
public class BoxPlacementFinder {

    /**
     * Находит подходящую позицию для посылки в кузове грузовика
     * Позиция считается подходящей, если посылка помещается по размерам, а имеет опору необходимой площади
     *
     * @param truckBody матрица - кузов грузовика
     * @param boxHeight высота посылки
     * @param boxLength длина посылки
     * @return массив с координатами (формат [вертикаль; горизонталь] позиций для размещения посылки
     * Если подходящего места нет, возвращается пустой массив
     */

    public int[] findPositionForBox(Integer[][] truckBody, int boxHeight, int boxLength) {
        log.info("Поиск позиции для посылки: Высота = {}, Длина = {}", boxHeight, boxLength);

        for (int i = truckBody.length - 1; i >= boxHeight - 1; i--) {
            int[] position = findPositionInRow(truckBody, i, boxHeight, boxLength);
            if (position.length > 0) {
                log.info("Найдена подходящая позиция на строке {}: Верт. = {}, Гориз. = {}", i, position[0], position[1]);
                return position;
            }
        }

        log.warn("Подходящая позиция для посылки не найдена.");
        return new int[0];
    }

    private int[] findPositionInRow(Integer[][] truckBody, int row, int boxHeight, int boxLength) {
        log.debug("Поиск в строке {} для посылки с высотой {} и длиной {}", row, boxHeight, boxLength);
        int left = Integer.MIN_VALUE;
        int right = 0;

        while (right < truckBody[row].length) {
            if (isBoxFit(truckBody, row, right, boxHeight, boxLength)) {
                boolean isSupported = isPositionSupported(truckBody, boxLength, row, right);
                if (isSupported) {
                    log.debug("Позиция найдена: строка {}, правая граница = {}", row, right);
                    return new int[]{row, right - boxLength + 1};
                }
            }

            left = updateLeftBound(truckBody, row, right, left);
            right++;
        }

        log.debug("Позиция в строке {} не найдена", row);
        return new int[0];
    }

    private int updateLeftBound(Integer[][] truckBody, int row, int right, int left) {
        if (Objects.isNull(truckBody[row][right])) {
            if (left == Integer.MIN_VALUE) {
                log.debug("Обновление левой границы: новая левая граница = {}", right);
                return right;
            }
        } else {
            log.debug("Левая граница сброшена на минимальное значение.");
            return Integer.MIN_VALUE;
        }
        return left;
    }

    private boolean isPositionSupported(Integer[][] truckBody, int boxLength, int row, int right) {
        boolean hasSupport = (row == truckBody.length - 1) || hasEnoughSupport(truckBody[row + 1]
                , boxLength, right - boxLength + 1, right);
        log.debug("Проверка поддержки позиции в строке {}: результат = {}", row, hasSupport);
        return hasSupport;
    }

    private boolean isBoxFit(Integer[][] truckBody, int row, int right, int boxHeight, int boxLength) {
        int left = right - boxLength + 1;

        if (left < 0 || isNotEmptySpace(truckBody[row], left, right)) {
            log.debug("Посылка не помещается в строке {}: левая граница = {}, правая граница = {}", row, left, right);
            return false;
        }

        for (int h = 0; h < boxHeight; h++) {
            if (isNotEmptySpace(truckBody[row - h], left, right)) {
                log.debug("Посылка не помещается по высоте: строка {}, высота = {}", row - h, boxHeight);
                return false;
            }
        }

        log.debug("Посылка помещается в строке {}: левая граница = {}, правая граница = {}", row, left, right);
        return true;
    }

    private boolean isNotEmptySpace(Integer[] row, int left, int right) {
        for (int i = left; i <= right; i++) {
            if (!Objects.isNull(row[i])) {
                log.debug("Найдена занятая ячейка в диапазоне [{}, {}]: индекс = {}", left, right, i);
                return true;
            }
        }
        return false;
    }

    private boolean hasEnoughSupport(Integer[] truckBody, int boxLength, int left, int right) {
        int halfBoxLength = (boxLength % 2 == 0) ? boxLength / 2 : boxLength / 2 + 1;
        int supportLength = 0;
        while (left <= right) {
            if (!Objects.isNull(truckBody[left])) {
                supportLength++;
            }
            left++;
        }

        boolean hasEnoughSupport = supportLength >= halfBoxLength;
        log.debug("Проверка достаточной поддержки: необходимо {} элементов, найдено {}. Результат = {}"
                , halfBoxLength, supportLength, hasEnoughSupport);
        return hasEnoughSupport;
    }

    /**
     * Осуществляет поиск наименее загруженного грузовика
     *
     * @param trucks список грузовиков
     * @return наименее загруженный грузовик
     */
    public Truck findTruckWithMinLoadCapacity(List<Truck> trucks) {
        Truck truckWithMinLoadCapacity = trucks.get(0);

        for (Truck truck : trucks) {
            if (truck.getLoadCapacity() < truckWithMinLoadCapacity.getLoadCapacity()) {
                truckWithMinLoadCapacity = truck;
            }
        }
        return truckWithMinLoadCapacity;
    }
}
