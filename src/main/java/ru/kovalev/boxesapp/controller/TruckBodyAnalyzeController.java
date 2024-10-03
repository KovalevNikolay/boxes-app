package ru.kovalev.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.io.TruckReader;
import ru.kovalev.boxesapp.model.Box;
import ru.kovalev.boxesapp.model.Truck;
import ru.kovalev.boxesapp.printer.TruckAnalyzePrinter;
import ru.kovalev.boxesapp.service.TruckLoadAnalyzer;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TruckBodyAnalyzeController {
    private final TruckLoadAnalyzer truckLoadAnalyzer;
    private final TruckReader truckReader;
    private final TruckAnalyzePrinter truckAnalyzePrinter;

    public String analyze(String path) {
        List<Truck> trucks = truckReader.read(Path.of(path));
        StringBuilder result = new StringBuilder();
        for (Truck truck : trucks) {
            Map<Box, Integer> analyzeResult = truckLoadAnalyzer.analyze(truck);
            result.append(truckAnalyzePrinter.print(truck, analyzeResult));
        }
        return result.toString();
    }
}
