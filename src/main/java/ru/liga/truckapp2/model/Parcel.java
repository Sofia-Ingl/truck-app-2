package ru.liga.truckapp2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;

@Getter
@AllArgsConstructor
public class Parcel {

    private ParcelType type;

    public final static Comparator<Parcel> widthComparator =
            Comparator.comparingInt((Parcel p) -> p.getType().getShape()[0].length)
                    .thenComparingInt(p -> p.getType().getShape().length);

    public final static Comparator<Parcel> heightComparator =
            Comparator.comparingInt((Parcel p) -> p.getType().getShape().length)
                    .thenComparingInt(p -> p.getType().getShape()[0].length);

}
