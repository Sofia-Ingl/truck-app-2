package ru.liga.truckapp2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class ParcelCreateDto {
    private String name;
    private boolean[][] shape;
    private char symbol;
}
