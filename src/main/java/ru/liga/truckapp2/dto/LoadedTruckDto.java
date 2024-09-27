package ru.liga.truckapp2.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class LoadedTruckDto {

    private String[] back;
    private List<ParcelDto> parcels;

}
