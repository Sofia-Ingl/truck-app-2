package ru.liga.truckapp2.util;

import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.ParcelTypeCreateDto;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.exception.AppException;

import java.util.Objects;

@Component
public class DefaultInputDtoCreator implements InputDtoCreator {

    public ParcelTypeDto makeUpdateDto(String newName,
                                       String newShape,
                                       Character newSymbol) {
        return ParcelTypeDto.builder()
                .name(Objects.equals(newName, "") ? null : newName.trim())
                .shape(Objects.equals(newShape, "") ? null : getShapeFromString(newShape))
                .symbol(Objects.equals(newSymbol, ' ') ? null : newSymbol)
                .build();
    }

    public ParcelTypeCreateDto makeCreateDto(String name,
                                             String shape,
                                             Character symbol) {

        return new ParcelTypeCreateDto(
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
