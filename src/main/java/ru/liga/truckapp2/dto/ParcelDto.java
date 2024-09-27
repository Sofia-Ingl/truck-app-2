package ru.liga.truckapp2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@Builder
public class ParcelDto {
    private String name;
    private boolean[][] shape;
    private Character symbol;

    @Override
    public String toString() {
        return "ParcelDto{" +
                "name='" + name + '\'' +
                ", shape=" + Arrays.deepToString(shape) +
                ", symbol=" + symbol +
                '}';
    }
}
