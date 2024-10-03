package ru.liga.truckapp2.model;

import org.junit.jupiter.api.Test;
import ru.liga.truckapp2.model.inner.Coordinates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TruckTest {

    @Test
    void canLoadParcel() {

        char[][] back = new char[][]{
                {'1', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
        };
        Truck truck = new Truck(
                4, 2, back
        );

        ParcelType parcelType = new ParcelType(
                "3",
                new boolean[][]{{true, true, true}},
                '3'
        );
        Parcel parcel = new Parcel(
                parcelType
        );

        boolean canLoad = truck.canLoadParcel(0, 0, parcel);
        assertThat(canLoad).isFalse();
        canLoad = truck.canLoadParcel(1, 0, parcel);
        assertThat(canLoad).isTrue();
        canLoad = truck.canLoadParcel(0, 1, parcel);
        assertThat(canLoad).isTrue();

    }

    @Test
    void loadParcelWithoutCheck() {

        char[][] back = new char[][]{
                {' ', ' ', ' '}
        };
        Truck truck = new Truck(
                3, 1, back
        );

        ParcelType parcelType = new ParcelType(
                "3",
                new boolean[][]{{true, true, true}},
                '3'
        );
        Parcel parcel = new Parcel(
                parcelType
        );

        truck.loadParcelWithoutCheck(0, 0, parcel);

        assertThat(truck.getBack()[0]).isEqualTo(new char[]{'3', '3', '3'});

    }


    @Test
    void loadParcelWithoutCheckWhenOutOfBounds() {

        char[][] back = new char[][]{
                {' ', ' ', ' '}
        };
        Truck truck = new Truck(
                3, 1, back
        );

        ParcelType parcelType = new ParcelType(
                "3",
                new boolean[][]{{true, true, true}},
                '3'
        );
        Parcel parcel = new Parcel(
                parcelType
        );

        assertThatThrownBy(() ->
                truck.loadParcelWithoutCheck(1, 0, parcel)
        ).isInstanceOf(ArrayIndexOutOfBoundsException.class);

    }


    @Test
    void findPlaceForParcel() {
        char[][] back = new char[][]{
                {'o', 'o', 'o'},
                {'o', ' ', 'o'},
                {'o', 'o', 'o'}
        };
        Truck truck = new Truck(
                3, 3, back
        );

        ParcelType parcelType = new ParcelType(
                "1",
                new boolean[][]{{true}},
                '1'
        );
        Parcel parcel = new Parcel(
                parcelType
        );

        Coordinates coordinates = truck.findPlaceForParcel(parcel);
        assertThat(coordinates.getX()).isEqualTo(1);
        assertThat(coordinates.getY()).isEqualTo(1);

    }

    @Test
    void findPlaceForParcelWhenNoPlaceCanBeFound() {
        char[][] back = new char[][]{
                {'1', ' ', ' '}
        };
        Truck truck = new Truck(
                3, 1, back
        );

        ParcelType parcelType = new ParcelType(
                "3",
                new boolean[][]{{true, true, true}},
                '3'
        );
        Parcel parcel = new Parcel(
                parcelType
        );

        Coordinates coordinates = truck.findPlaceForParcel(parcel);
        assertThat(coordinates.getX()).isEqualTo(-1);
        assertThat(coordinates.getY()).isEqualTo(-1);

    }
}