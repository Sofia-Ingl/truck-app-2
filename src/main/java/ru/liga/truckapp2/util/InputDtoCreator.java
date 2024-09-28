package ru.liga.truckapp2.util;

import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.exception.AppException;

import java.util.Objects;

@Component
public class InputDtoCreator {

    public ParcelDto makeUpdateDto(String newName,
                                   String newShape,
                                   Character newSymbol) {
        return ParcelDto.builder()
                .name(Objects.equals(newName, "") ? null : newName.trim())
                .shape(Objects.equals(newShape, "") ? null : getShapeFromString(newShape))
                .symbol(Objects.equals(newSymbol, ' ') ? null : newSymbol)
                .build();
    }

    public ParcelDto makeCreateDto(String name,
                                   String shape,
                                   Character symbol) {

        return new ParcelDto(
                name.trim(),
                getShapeFromString(shape),
                symbol
        );
    }

    private boolean[][] getShapeFromString(String shape) {
        String[] lines = shape.split(",");
        if (lines.length == 0) {
            throw new AppException("Invalid shape: " + shape);
        }
        int height = lines.length;
        int width = lines[0].length();
        boolean[][] shapeArr = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            if (lines[i].length() != width) {
                throw new AppException("Invalid shape: " + shape + "; line of length " + width + " expected");
            }
            for (int j = 0; j < lines[i].length(); j++) {
                shapeArr[i][j] = lines[i].charAt(j) != ' ';
            }
        }
        return shapeArr;
    }

}
