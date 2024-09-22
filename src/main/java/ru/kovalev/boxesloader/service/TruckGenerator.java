package ru.kovalev.boxesloader.service;

import lombok.extern.slf4j.Slf4j;
import ru.kovalev.boxesloader.model.Truck;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TruckGenerator {

    public List<Truck> generate(int countTrucks, int bodyHeight, int bodyLength) {
        List<Truck> trucks = new ArrayList<>();

        for (int i = 0; i < countTrucks; i++) {
            trucks.add(new Truck(bodyHeight, bodyLength));
        }
        log.debug("Создан список грузовиков: {} шт., высота кузова= {}, длина кузова= {}", countTrucks, bodyHeight, bodyLength);
        return trucks;
    }
}
