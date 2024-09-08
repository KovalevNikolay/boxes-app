package ru.kovalev.boxesloader.service;

import ru.kovalev.boxesloader.model.Truck;
import ru.kovalev.boxesloader.util.BoxesManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        Optional<int[]> position = findPositionForBox(truckBody, boxHeight, boxLength);

        if (position.isEmpty()) {
            return false;
        }

        performLoading(boxKey, truckBody, boxDimensions, position.get()[0], position.get()[1]);
        return true;
    }

    private Optional<int[]> findPositionForBox(String[][] truckBody, int boxHeight, int boxLength) {
        for (int i = truckBody.length - 1; i >= boxHeight - 1; i--) {
            int left = Integer.MIN_VALUE;
            int right = 0;

            while (right < truckBody[i].length) {
                if (isBoxFit(truckBody, i, right, boxHeight, boxLength)) {
                    boolean isSupported = (i == truckBody.length - 1)
                                          || isThereSupport(truckBody[i + 1], boxLength, right - boxLength + 1, right);
                    if (isSupported) {
                        return Optional.of(new int[]{i, right - boxLength + 1});
                    }
                }

                if (truckBody[i][right].isEmpty()) {
                    if (left == Integer.MIN_VALUE) {
                        left = right;
                    }
                } else {
                    left = Integer.MIN_VALUE;
                }

                right++;
            }
        }
        return Optional.empty();
    }

    private boolean isBoxFit(String[][] truckBody, int row, int right, int boxHeight, int boxLength) {
        int left = right - boxLength + 1;

        if (left < 0 || !isEmptySpace(truckBody[row], left, right)) {
            return false;
        }

        for (int h = 0; h < boxHeight; h++) {
            if (!isEmptySpace(truckBody[row - h], left, right)) {
                return false;
            }
        }

        return true;
    }

    private boolean isEmptySpace(String[] row, int left, int right) {
        for (int i = left; i <= right; i++) {
            if (!row[i].isEmpty()) {
                return false;
            }
        }
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
