package ru.liga.truckapp2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class Truck {

    private final int width;
    private final int height;
    private final char[][] back;

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

}
