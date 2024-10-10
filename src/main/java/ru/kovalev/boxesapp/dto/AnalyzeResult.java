package ru.kovalev.boxesapp.dto;

import lombok.Data;

import java.util.Map;

@Data
public class AnalyzeResult {
    private Map<BoxDto, Integer> result;
    private Truck truck;

    public AnalyzeResult(Map<BoxDto, Integer> result, Truck truck) {
        this.result = result;
        this.truck = truck;
    }

    @Override
    public String toString() {
        StringBuilder resultAnalyze = new StringBuilder();
        resultAnalyze.append("Грузовик:\n").append(truck).append("\nПосылки:");
        for (Map.Entry<BoxDto, Integer> box : result.entrySet()) {
            resultAnalyze.append(box.getKey().getName())
                    .append(" - ")
                    .append(box.getValue())
                    .append(" шт.");
        }
        return resultAnalyze.append("\n").toString();
    }
}
