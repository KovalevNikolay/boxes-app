package ru.kovalev.boxesapp.printer;

import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.dto.Truck;

import java.util.List;

@Service
public class TruckListPrinter {
    public String print(List<Truck> trucks) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Truck truck : trucks) {
            stringBuilder.append(truck).append("\n");
        }
        return stringBuilder.toString();
    }
}
