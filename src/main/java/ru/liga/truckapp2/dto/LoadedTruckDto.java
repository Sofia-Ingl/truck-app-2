package ru.liga.truckapp2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class LoadedTruckDto {

    private String[] back;
    private List<ParcelDto> parcels;

}
