package ru.kovalev.boxesapp.printer;

import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.model.Truck;

import java.util.Map;

@Service
public class TruckAnalyzePrinter {
    public String print(Truck truck, Map<Box, Integer> boxesInTruck) {
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
}
