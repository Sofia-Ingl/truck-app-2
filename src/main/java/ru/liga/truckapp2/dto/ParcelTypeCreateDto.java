package ru.liga.truckapp2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@AllArgsConstructor
@Getter
@Builder
public class ParcelTypeCreateDto {
    @NotBlank
    private String name;
    @NotNull
    private boolean[][] shape;
    @NotNull
    private Character symbol;

    @Override
    public String toString() {
        return "ParcelTypeCreateDto{" +
                "name='" + name + '\'' +
                ", shape=" + Arrays.deepToString(shape) +
                ", symbol=" + symbol +
                '}';
    }
}
