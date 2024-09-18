package ru.kovalev.boxesloader.service;

import java.util.Objects;

public class TruckSpaceFinder {

    public int[] findPositionForBox(String[][] truckBody, int boxHeight, int boxLength) {
        for (int i = truckBody.length - 1; i >= boxHeight - 1; i--) {
            int[] position = findPositionInRow(truckBody, i, boxHeight, boxLength);
            if (position.length > 0) {
                return position;
            }
        }
        return new int[0];
    }

    private int[] findPositionInRow(String[][] truckBody, int row, int boxHeight, int boxLength) {
        int left = Integer.MIN_VALUE;
        int right = 0;

        while (right < truckBody[row].length) {
            if (isBoxFit(truckBody, row, right, boxHeight, boxLength)) {
                boolean isSupported = isPositionSupported(truckBody, boxLength, row, right);
                if (isSupported) {
                    return new int[]{row, right - boxLength + 1};
                }
            }

            left = updateLeftBound(truckBody, row, right, left);
            right++;
        }
        return new int[0];
    }

    private int updateLeftBound(String[][] truckBody, int row, int right, int left) {
        if (Objects.isNull(truckBody[row][right])) {
            if (left == Integer.MIN_VALUE) {
                return right;
            }
        } else {
            return Integer.MIN_VALUE;
        }
        return left;
    }

    private boolean isPositionSupported(String[][] truckBody, int boxLength, int row, int right) {
        return (row == truckBody.length - 1) || hasEnoughSupport(truckBody[row + 1], boxLength, right - boxLength + 1, right);
    }

    private boolean isBoxFit(String[][] truckBody, int row, int right, int boxHeight, int boxLength) {
        int left = right - boxLength + 1;

        if (left < 0 || isNotEmptySpace(truckBody[row], left, right)) {
            return false;
        }

        for (int h = 0; h < boxHeight; h++) {
            if (isNotEmptySpace(truckBody[row - h], left, right)) {
                return false;
            }
        }
        return true;
    }

    private boolean isNotEmptySpace(String[] row, int left, int right) {
        for (int i = left; i <= right; i++) {
            if (!Objects.isNull(row[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean hasEnoughSupport(String[] truckBody, int boxLength, int left, int right) {
        int halfBoxLength = (boxLength % 2 == 0) ? boxLength / 2 : boxLength / 2 + 1;
        int supportLength = 0;
        while (left <= right) {
            if (!Objects.isNull(truckBody[left])) {
                supportLength++;
            }
            left++;
        }
        return supportLength >= halfBoxLength;
    }
}
