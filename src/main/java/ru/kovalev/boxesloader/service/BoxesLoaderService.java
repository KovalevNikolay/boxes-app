package ru.kovalev.boxesloader.service;

import ru.kovalev.boxesloader.model.Truck;
import ru.kovalev.boxesloader.util.BoxesManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BoxesLoaderService {
    private static final BoxesLoaderService INSTANCE = new BoxesLoaderService();

    private BoxesLoaderService() {
    }

    public List<Truck> distributeBoxes(Map<String, Integer> boxes) {
        if (boxes.isEmpty()) {
            return Collections.emptyList();
        }

        List<Truck> trucks = new ArrayList<>();
        trucks.add(new Truck());
        for (Map.Entry<String, Integer> box : boxes.entrySet()) {
            int countBoxesCurrentSize = box.getValue();
            while (countBoxesCurrentSize > 0) {
                boolean isLoad = false;
                for (Truck truck : trucks) {
                    isLoad = loadToTruck(box, truck);
                    if (isLoad) {
                        break;
                    }
                }

                if (!isLoad) {
                    Truck truck = new Truck();
                    loadToTruck(box, truck);
                    trucks.add(truck);
                }

                countBoxesCurrentSize--;
            }
        }

        return trucks;
    }

    private boolean loadToTruck(Map.Entry<String, Integer> box, Truck truck) {
        String boxKey = box.getKey();
        String[][] truckBody = truck.getBody();
        int[][] boxDimensions = BoxesManager.getBoxDimensions(boxKey);
        int boxHeight = boxDimensions.length;
        int boxLength = getMaxBoxLength(boxDimensions);
        boolean isLoaded = false;
        boolean isSupported;
        String current;

        outerLoop:
        for (int i = truckBody.length - 1; i >= boxHeight - 1; i--) {
            int right = 0;
            int left = Integer.MIN_VALUE;
            while (right < truckBody[i].length) {

                current = truckBody[i][right];

                if (boxLength == 1 && current.isEmpty()) {
                    isSupported = (i == truckBody.length - 1) || isThereSupport(truckBody[i + 1], boxLength, right, right);

                    if (isSupported) {
                        performLoading(boxKey, truckBody, boxDimensions, i, right);
                        isLoaded = true;
                        break outerLoop;
                    }
                }

                if (right - left + 1 == boxLength) {
                    isSupported = (i == truckBody.length - 1) || isThereSupport(truckBody[i + 1], boxLength, left, right);

                    if (isSupported) {
                        performLoading(boxKey, truckBody, boxDimensions, i, left);
                        isLoaded = true;
                        break outerLoop;
                    } else {
                        left++;
                    }
                }

                if (current.isEmpty()) {
                    if (left < 0) {
                        left = right;
                    }
                } else {
                    left = Integer.MIN_VALUE;
                }
                right++;
            }
        }

        return isLoaded;
    }

    private void performLoading(String boxKey, String[][] truckBody, int[][] boxDimensions, int vertical, int left) {
        for (int i = boxDimensions.length - 1; i >= 0; i--) {
            for (int j = 0; j < boxDimensions[i].length; j++) {
                truckBody[vertical][left + j] = boxKey;
            }
            vertical--;
        }
    }

    private boolean isThereSupport(String[] truckBody, int boxLength, int left, int right) {
        int halfBoxLength = (boxLength % 2 == 0) ? boxLength / 2 : boxLength / 2 + 1;
        int supportLength = 0;
        while (left <= right) {
            if (!truckBody[left].isEmpty()) {
                supportLength++;
            }
            left++;
        }
        return supportLength >= halfBoxLength;
    }

    private int getMaxBoxLength(int[][] boxDimensions) {
        int maxBoxLength = 0;
        for (int[] boxSize : boxDimensions) {
            maxBoxLength = Math.max(boxSize.length, maxBoxLength);
        }
        return maxBoxLength;
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
