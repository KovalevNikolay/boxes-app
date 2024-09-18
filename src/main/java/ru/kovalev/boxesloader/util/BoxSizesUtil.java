package ru.kovalev.boxesloader.util;

import java.util.Map;

public final class BoxSizesUtil {
    private static final Map<String, int[][]> BOX_SIZES = Map.of(
            "1", new int[1][1],
            "2", new int[1][2],
            "3", new int[1][3],
            "4", new int[1][4],
            "5", new int[1][5],
            "6", new int[2][3],
            "7", new int[][]{{0, 0, 0}, {0, 0, 0, 0}},
            "8", new int[2][4],
            "9", new int[3][3]
    );

    private BoxSizesUtil() {
    }

    public static int[][] getBoxDimensions(String boxWeight) {
        return BOX_SIZES.get(boxWeight);
    }
}
