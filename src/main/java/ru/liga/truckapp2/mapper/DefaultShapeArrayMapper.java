package ru.liga.truckapp2.mapper;

import org.springframework.stereotype.Component;

@Component
public class DefaultShapeArrayMapper implements ShapeArrayMapper {

    private final String SHAPE_LINES_DELIMITER = ",";
    private final char SHAPE_BLANK_POSITION_SYMBOL = ' ';
    private final char SHAPE_FILLED_POSITION_SYMBOL = '+';

    @Override
    public String shapeToString(boolean[][] shapeArray) {
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

    @Override
    public boolean[][] stringToShape(String shapeString) {
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
