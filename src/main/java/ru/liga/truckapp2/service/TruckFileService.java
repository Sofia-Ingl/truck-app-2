package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.LoadedTruckDto;

import java.util.List;

public interface TruckFileService {

    List<LoadedTruckDto> readTrucks(String fileName);

}
