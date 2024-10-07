package ru.kovalev.boxesapp.mapper;

import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.model.Truck;

import java.util.ArrayList;
import java.util.List;

@Service
public class TruckMapper {
    private static final int HEIGHT_INDEX = 0;
    private static final int LENGTH_INDEX = 1;

    public List<Truck> mapToList(String input) {
        List<Truck> trucks = new ArrayList<>();
        String[] truckSizes = input.split(",");

        for (String truckSize : truckSizes) {
            String[] size = truckSize.split("x");
            int height = Integer.parseInt(size[HEIGHT_INDEX]);
            int length = Integer.parseInt(size[LENGTH_INDEX]);
            trucks.add(new Truck(height, length));
        }

        return trucks;
    }
}
