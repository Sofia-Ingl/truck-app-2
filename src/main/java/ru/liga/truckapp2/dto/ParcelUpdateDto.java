package ru.liga.truckapp2.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParcelUpdateDto {
    private String name;
    private boolean[][] shape;
    private Character symbol;
}
