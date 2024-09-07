package ru.kovalev.boxesloader.service;

import ru.kovalev.boxesloader.model.Truck;
import ru.kovalev.boxesloader.util.BoxesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoxesLoaderService {
    private static final BoxesLoaderService INSTANCE = new BoxesLoaderService();

    private BoxesLoaderService() {
    }

    public List<Truck> distributeBoxesToTruck(Map<String, Integer> boxes) {
        List<Truck> trucks = new ArrayList<>();
        List<String> boxesForDelete = new ArrayList<>();

        while (!boxes.isEmpty()) {
            Truck truck = new Truck();
            boolean isLoaded = true;
            for (Map.Entry<String, Integer> box : boxes.entrySet()) {
                String boxWeight = box.getKey();
                int boxCount = box.getValue();
                int[][] boxDimensions = BoxesManager.getSize(boxWeight);
                while (isLoaded) {
                    isLoaded = boxLoading(truck, boxWeight, boxDimensions);
                    if (isLoaded) {
                        boxCount--;

                        if (boxCount > 0) {
                            boxes.put(boxWeight, boxCount);
                        } else {
                            boxesForDelete.add(boxWeight);
                            break;
                        }
                    }
                }
            }

            for (String box : boxesForDelete) {
                boxes.remove(box);
            }

            if (!truck.isEmpty()) {
                trucks.add(truck);
            }
        }

        return trucks;
    }

    private boolean boxLoading(Truck truck, String boxWeight, int[][] boxDimensions) {
        String[][] truckBody = truck.getBody();
        int needLength = getMaxBoxLength(boxDimensions);
        int horizontalIndex = 0;
        for (int i = truckBody.length - 1; i >= 0; i--) {
            int availableLength = 0;
            for (int j = 0; j < truckBody[i].length && needLength != availableLength; j++) {
                if (truckBody[i][j].isEmpty()) {
                    availableLength++;
                    horizontalIndex = j;
                } else {
                    availableLength = 0;
                }
            }

            int verticalIndex = i - boxDimensions.length + 1;

            if (needLength == availableLength && verticalIndex >= 0) {
                performBoxLoadingToTruck(truckBody, boxWeight, boxDimensions, horizontalIndex, verticalIndex, needLength);
                return true;
            }
        }
        return false;
    }

    private int getMaxBoxLength(int[][] box) {
        int maxBoxLength = 0;
        for (int i = 0; i < box.length; i++) {
            maxBoxLength = Math.max(box[i].length, maxBoxLength);
        }
        return maxBoxLength;
    }

    private void performBoxLoadingToTruck(String[][] truckBody, String boxWeight, int[][] boxDimensions,
                                          int horizontalIndex, int verticalIndex, int maxLength)
    {
        for (int i = 0; i < boxDimensions.length; i++) {
            for (int j = 0; j < boxDimensions[i].length; j++) {
                if (boxDimensions[i].length == maxLength) {
                    truckBody[verticalIndex + i][horizontalIndex - j] = boxWeight;
                } else {
                    truckBody[verticalIndex + i][horizontalIndex - j - 1] = boxWeight;
                }
            }
        }
    }

    public void printTrucks(List<Truck> trucks) {
        for (Truck truck : trucks) {
            System.out.println(truck);
            System.out.println();
        }
    }

    public static BoxesLoaderService getInstance() {
        return INSTANCE;
    }
}
