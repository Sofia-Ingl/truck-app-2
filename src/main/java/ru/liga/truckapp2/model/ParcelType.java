package ru.liga.truckapp2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Data
@Table(schema = "truck", name = "parcel_types")
@NoArgsConstructor
@AllArgsConstructor
public class ParcelType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Type(type = "ru.liga.truckapp2.model.type.DoubleDimensionalBooleanArrayUserType")
    private boolean[][] shape;
    private char symbol;

    public ParcelType(String name, boolean[][] shape, char symbol) {
        this.name = name;
        this.shape = shape;
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParcelType other) {
            boolean equalNames = other.getName().equals(this.getName());
            boolean equalShapes = Arrays.deepEquals(other.getShape(), this.getShape());
            boolean equalSymbols = other.symbol == this.getSymbol();
            return equalNames && equalShapes && equalSymbols;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ParcelType{" +
                "name='" + name + '\'' +
                ", shape=" + Arrays.deepToString(shape) +
                ", symbol=" + symbol +
                '}';
    }
}
