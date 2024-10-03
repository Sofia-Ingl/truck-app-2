package ru.liga.truckapp2;

import lombok.AllArgsConstructor;
import org.mockito.ArgumentMatcher;

import java.util.Arrays;

@AllArgsConstructor
public class ArrayMatcher implements ArgumentMatcher<boolean[][]> {

    private boolean[][] left;

    @Override
    public boolean matches(boolean[][] right) {
        return Arrays.deepEquals(left, right);
    }

}
