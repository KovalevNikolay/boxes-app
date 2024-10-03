package ru.kovalev.boxesapp.mapper;

import org.springframework.stereotype.Service;
import ru.kovalev.boxesapp.interfaces.Mapper;
import ru.kovalev.boxesapp.model.Truck;

import java.util.ArrayList;
import java.util.List;

@Service
public class TruckMapper implements Mapper<String, List<Truck>> {
    @Override
    public List<Truck> mapFrom(String input) {
        List<Truck> trucks = new ArrayList<>();
        String[] truckSizes = input.split(",");

        for (String truckSize : truckSizes) {
            String[] size = truckSize.split("x");
            int height = Integer.parseInt(size[0]);
            int length = Integer.parseInt(size[1]);
            trucks.add(new Truck(height, length));
        }

        return trucks;
    }
}
