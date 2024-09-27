package ru.liga.truckapp2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Parcel {
    private String name;
    private boolean[][] shape;
    private char symbol;
}
