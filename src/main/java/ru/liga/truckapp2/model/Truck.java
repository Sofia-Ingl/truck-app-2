package ru.liga.truckapp2.model;

import lombok.Getter;
import ru.liga.truckapp2.model.inner.Coordinates;

import java.util.Arrays;
import java.util.Comparator;

@Getter
public class Truck {

    private final int width;
    private final int height;
    private final char[][] back;

    public final static Comparator<Truck> volumeComparator =
            Comparator.comparingInt(t -> t.getWidth() * t.getHeight());

    public Truck(Integer width, Integer height) {
        back = new char[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(back[i], ' ');
        }

        this.width = width;
        this.height = height;
    }


    public Truck(Integer width, Integer height, char[][] back) {

        this.back = new char[height][width];

        for (int i = 0; i < height; i++) {
            System.arraycopy(back[i], 0, this.back[i], 0, width);
        }
        this.width = width;
        this.height = height;

    }


    /**
     * Функция, проверяющая, можно ли загрузить посылку на заданное место в грузовике
     *
     * @param x      позиция по горизонтали
     * @param y      позиция по вертикали
     * @param parcel посылка
     * @return можно ли загрузить посылку
     */
    public boolean canLoadParcel(int x,
                                 int y,
                                 Parcel parcel) {

        int parcelHeight = parcel.getType().getShape().length;
        int parcelWidth = parcel.getType().getShape()[0].length;
        if (parcelHeight > height - y
                || parcelWidth > width - x) {
            return false;
        }

        for (int i = 0; i < parcelHeight; i++) {
            for (int j = 0; j < parcelWidth; j++) {

                int yBackCoordinate = y + i;
                int xBackCoordinate = x + j;

                if (back[yBackCoordinate][xBackCoordinate] != ' '
                        && parcel.getType().getShape()[parcelHeight - 1 - i][j]) {
                    return false;
                }

            }
        }
        return true;
    }


    /**
     * Процедура, загружающая посылку на заданное место в грузовике без проверки
     *
     * @param x      позиция по горизонтали
     * @param y      позиция по вертикали
     * @param parcel посылка
     */
    public void loadParcelWithoutCheck(int x,
                                       int y,
                                       Parcel parcel) {

        int parcelHeight = parcel.getType().getShape().length;
        int parcelWidth = parcel.getType().getShape()[0].length;

        for (int i = 0; i < parcelHeight; i++) {

            for (int j = 0; j < parcelWidth; j++) {

                int yBackCoordinate = y + i;
                int xBackCoordinate = x + j;

                boolean currentSymbolNotBlank = parcel.getType().getShape()[parcelHeight - 1 - i][j];

                if (currentSymbolNotBlank) {
                    back[yBackCoordinate][xBackCoordinate] = parcel.getType().getSymbol();
                }

            }
        }

    }


    public Coordinates findPlaceForParcel(Parcel parcel) {

        int parcelHeight = parcel.getType().getShape().length;
        int parcelWidth = parcel.getType().getShape()[0].length;

        for (int i = 0; i <= height - parcelHeight; i++) {
            for (int j = 0; j <= width - parcelWidth; j++) {

                // todo: check hangings
                if (canLoadParcel(j, i, parcel)) {
                    return new Coordinates(j, i);
                }

            }
        }
        return new Coordinates(-1, -1);

    }

}
