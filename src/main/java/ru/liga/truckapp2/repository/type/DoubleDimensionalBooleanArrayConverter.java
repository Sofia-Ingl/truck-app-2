package ru.liga.truckapp2.repository.type;

import lombok.extern.slf4j.Slf4j;

@Slf4j

public class DoubleDimensionalBooleanArrayConverter  {
    private static final String SHAPE_LINES_DELIMITER = ",";
    private static final char SHAPE_BLANK_POSITION_SYMBOL = ' ';
    private static final char SHAPE_FILLED_POSITION_SYMBOL = '+';

    public static String toString(boolean[][] shapeArray) {
        String[] shapeLines = new String[shapeArray.length];
        for (int i = 0; i < shapeArray.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < shapeArray[i].length; j++) {
                if (shapeArray[i][j]) {
                    sb.append(SHAPE_FILLED_POSITION_SYMBOL);
                } else {
                    sb.append(SHAPE_BLANK_POSITION_SYMBOL);
                }
            }
            shapeLines[i] = sb.toString();
        }
        return String.join(SHAPE_LINES_DELIMITER, shapeLines);
    }


    public static boolean[][] fromString(String shapeString) {
        String[] shapeStringsArr = shapeString.split(SHAPE_LINES_DELIMITER);
        boolean[][] shape = new boolean[shapeStringsArr.length][shapeStringsArr[0].length()];
        for (int i = 0; i < shapeStringsArr.length; i++) {
            for (int j = 0; j < shapeStringsArr[i].length(); j++) {
                shape[i][j] = (shapeStringsArr[i].charAt(j) != SHAPE_BLANK_POSITION_SYMBOL);
            }
        }
        return shape;
    }
}
