package ru.liga.truckapp2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.liga.truckapp2.model.ParcelType;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@Builder
public class ParcelDto {

    private String name;
    private boolean[][] shape;
    private Character symbol;

    public static ParcelDto of(ParcelType parcelType) {
        boolean[][] shape = new boolean[parcelType.getShape().length][parcelType.getShape()[0].length];
        for (int i = 0; i < parcelType.getShape().length; i++) {
            System.arraycopy(
                    parcelType.getShape()[i],
                    0,
                    shape[i],
                    0,
                    parcelType.getShape()[0].length
            );
        }
        return new ParcelDto(parcelType.getName(), shape, parcelType.getSymbol());
    }

    @Override
    public String toString() {
        return "ParcelDto{" +
                "name='" + name + '\'' +
                ", shape=" + Arrays.toString(shape) +
                ", symbol=" + symbol +
                '}';
    }
}
