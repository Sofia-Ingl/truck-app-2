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

    public final static Comparator<Parcel> volumeComparator =
            Comparator.comparingInt(Parcel::calculateVolume);

    /**
     * Вычисление объема посылки без учета пустых мест
     *
     * @return объем посылки
     */
    public int calculateVolume() {
        int volume = 0;
        for (int i = 0; i < this.getType().getShape().length; i++) {
            for (int j = 0; j < this.getType().getShape()[i].length; j++) {
                if (this.getType().getShape()[i][j]) {
                    volume++;
                }
            }
        }
        return volume;
    }

    @Override
    public String toString() {
        return "Parcel{" +
                "type=" + type.toString() +
                '}';
    }
}
