package ru.liga.truckapp2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class CountedTruckDto {

    private String[] back;
    private Map<String, Long> parcelQuantityByName;

}
