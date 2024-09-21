package ru.kovalev.boxesloader.service;

import ru.kovalev.boxesloader.model.Truck;

import java.util.HashMap;
import java.util.Map;

public class TruckLoadAnalyzer {

    public Map<Integer, Integer> countBoxesInTruck(Truck truck) {
        Integer[][] body = truck.getBody();
        Map<Integer, Integer> boxCount = new HashMap<>();
        for (Integer[] row : body) {
            for (Integer cell : row) {
                if (cell != null) {
                    int currentCount = boxCount.getOrDefault(cell, 0);
                    boxCount.put(cell, currentCount + 1);
                }
            }
        }

        boxCount.replaceAll((marker, countMarkers) -> countMarkers / marker);

        return boxCount;
    }
}
