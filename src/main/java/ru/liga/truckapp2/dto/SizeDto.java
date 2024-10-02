package ru.liga.truckapp2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SizeDto {
    private Integer width;
    private Integer height;

    @Override
    public String toString() {
        return "SizeDto{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
