package ru.kovalev.boxesloader.service;

import ru.kovalev.boxesloader.exception.OversizedBoxException;
import ru.kovalev.boxesloader.model.Truck;
import ru.kovalev.boxesloader.util.BoxSizesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoxesLoaderService {

    private final TruckSpaceFinder truckSpaceFinder;

    public BoxesLoaderService(TruckSpaceFinder truckSpaceFinder) {
        this.truckSpaceFinder = truckSpaceFinder;
    }

    public List<Truck> distributeBoxes(List<String> boxes, int truckBodySize) {

        if (boxes == null || boxes.isEmpty()) {
            return Collections.emptyList();
        }

        List<Truck> trucks = new ArrayList<>();
        trucks.add(new Truck(truckBodySize));
        for (String box : boxes) {
            boolean isLoad = false;
            for (Truck truck : trucks) {
                isLoad = loadToTruck(box, truck);
                if (isLoad) {
                    break;
                }
            }

            if (!isLoad) {
                Truck truck = new Truck(truckBodySize);
                loadToTruck(box, truck);
                trucks.add(truck);
            }

        }
        return trucks;
    }

    public List<Truck> distributeHowOneTruckOneBox(List<String> boxes, int truckBodySize) {
        if (boxes == null || boxes.isEmpty()) {
            return Collections.emptyList();
        }

        List<Truck> trucks = new ArrayList<>();

        for (String box : boxes) {
            Truck truck = new Truck(truckBodySize);
            loadToTruck(box, truck);
            trucks.add(truck);
        }

        return trucks;
    }

    private boolean loadToTruck(String box, Truck truck) {
        String[][] truckBody = truck.getBody();
        int[][] boxDimensions = BoxSizesUtil.getBoxDimensions(box);
        int boxHeight = boxDimensions.length;
        int boxLength = getMaxBoxLength(boxDimensions);

        if (boxHeight > truckBody.length || boxLength > truckBody[0].length) {
            throw new OversizedBoxException("Габариты посылки не могут превышать размеры кузова.");
        }

        int[] position = truckSpaceFinder.findPositionForBox(truckBody, boxHeight, boxLength);

        if (position.length == 0) {
            return false;
        }

        performLoading(box, truckBody, boxDimensions, position[0], position[1]);
        return true;
    }

    private void performLoading(String boxKey, String[][] truckBody, int[][] boxDimensions, int vertical, int left) {
        for (int i = boxDimensions.length - 1; i >= 0; i--) {
            for (int j = 0; j < boxDimensions[i].length; j++) {
                truckBody[vertical][left + j] = boxKey;
            }
            vertical--;
        }
    }

    private int getMaxBoxLength(int[][] boxDimensions) {
        int maxBoxLength = 0;
        for (int[] boxSize : boxDimensions) {
            maxBoxLength = Math.max(boxSize.length, maxBoxLength);
        }
        return maxBoxLength;
    }
}