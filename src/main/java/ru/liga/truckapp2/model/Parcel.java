package ru.liga.truckapp2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Setter
@Getter
@AllArgsConstructor
public class Parcel {
    private String name;
    private boolean[][] shape;
    private char symbol;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Parcel other) {
            boolean equalNames = other.getName().equals(this.getName());
            boolean equalShapes = Arrays.deepEquals(other.getShape(), this.getShape());
            boolean equalSymbols = other.symbol == this.getSymbol();
            return equalNames && equalShapes && equalSymbols;
        };
        return false;
    }
}
