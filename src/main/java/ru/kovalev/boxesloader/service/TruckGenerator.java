package ru.kovalev.boxesloader.service;

import lombok.extern.slf4j.Slf4j;
import ru.kovalev.boxesloader.model.Truck;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TruckGenerator {

    /**
     * Создает список грузовиков с заданными размерами кузова
     *
     * @param countTrucks количество грузовиков, которые нужно создать
     * @param bodyHeight высота кузова грузовика
     * @param bodyLength длина кузова грузовика
     * @return список созданных грузовиков
     * @throws IllegalArgumentException если высота или длина кузова меньше или равна нулю
     */
    public List<Truck> generate(int countTrucks, int bodyHeight, int bodyLength) {
        if (bodyHeight <= 0 || bodyLength <= 0) {
            throw new IllegalArgumentException("Размеры кузова грузовика не могут быть меньше или равны нулю.");
        }
        List<Truck> trucks = new ArrayList<>();

        for (int i = 0; i < countTrucks; i++) {
            trucks.add(new Truck(bodyHeight, bodyLength));
        }
        log.debug("Создан список грузовиков: {} шт., высота кузова= {}, длина кузова= {}", countTrucks, bodyHeight, bodyLength);
        return trucks;
    }
}
