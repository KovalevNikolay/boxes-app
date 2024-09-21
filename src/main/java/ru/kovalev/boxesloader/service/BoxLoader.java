package ru.kovalev.boxesloader.service;

import ru.kovalev.boxesloader.exception.OversizedBoxException;
import ru.kovalev.boxesloader.model.Box;
import ru.kovalev.boxesloader.model.Truck;

import java.util.*;

public class BoxLoader {

    private final static int ZERO_BOXES_IN_THE_TRUCK = 0;

    private final TruckSpaceFinder truckSpaceFinder;

    public BoxLoader(TruckSpaceFinder truckSpaceFinder) {
        this.truckSpaceFinder = truckSpaceFinder;
    }

    public List<Truck> distributeBoxes(List<Box> boxes, int truckHeigth, int truckLength) {

        if (boxes == null || boxes.isEmpty()) {
            return Collections.emptyList();
        }

        boxes.sort(Collections.reverseOrder());

        List<Truck> trucks = new ArrayList<>();
        trucks.add(new Truck(truckHeigth, truckLength));
        for (Box box : boxes) {
            boolean isLoad = false;
            for (Truck truck : trucks) {
                isLoad = loadToTruck(box, truck);
                if (isLoad) {
                    break;
                }
            }

            if (!isLoad) {
                Truck truck = new Truck(truckHeigth, truckLength);
                loadToTruck(box, truck);
                trucks.add(truck);
            }

        }
        return trucks;
    }

    public List<Truck> uniformLoadingBoxes(List<Box> boxes, int truckHeigth, int truckLength, int countBoxesInTruck) {
        if (boxes == null || boxes.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Truck, Integer> trucks = new HashMap<>();
        trucks.put(new Truck(truckHeigth, truckLength), ZERO_BOXES_IN_THE_TRUCK);

        for (Box box : boxes) {
            boolean isLoad = false;

            for (Map.Entry<Truck, Integer> truck : trucks.entrySet()) {
                Integer countLoadingBoxes = truck.getValue();
                if (countLoadingBoxes < countBoxesInTruck) {
                    Truck currentTruck = truck.getKey();
                    isLoad = loadToTruck(box, currentTruck);
                    if (isLoad) {
                        trucks.put(currentTruck, countLoadingBoxes + 1);
                        break;
                    }
                }
            }

            if (!isLoad) {
                Truck truck = new Truck(truckHeigth, truckLength);
                loadToTruck(box, truck);
                trucks.put(truck, ZERO_BOXES_IN_THE_TRUCK + 1);
            }
        }

        return trucks.keySet().stream().toList();
    }

    private boolean loadToTruck(Box box, Truck truck) {
        Integer[][] truckBody = truck.getBody();
        int[][] boxSizes = box.sizes();
        int boxHeight = box.getHeight();
        int boxLength = box.getMaxLength();

        if (boxHeight > truckBody.length || boxLength > truckBody[0].length) {
            throw new OversizedBoxException("Габариты посылки не могут превышать размеры кузова.");
        }

        int[] position = truckSpaceFinder.findPositionForBox(truckBody, boxHeight, boxLength);

        if (position.length == 0) {
            return false;
        }

        performLoading(box.getMarker(), truckBody, boxSizes, position[0], position[1]);
        return true;
    }

    private void performLoading(Integer marker, Integer[][] truckBody, int[][] boxDimensions, int vertical, int left) {
        for (int i = boxDimensions.length - 1; i >= 0; i--) {
            for (int j = 0; j < boxDimensions[i].length; j++) {
                truckBody[vertical][left + j] = marker;
            }
            vertical--;
        }
    }
}