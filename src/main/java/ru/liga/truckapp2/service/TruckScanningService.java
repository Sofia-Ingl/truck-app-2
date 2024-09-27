package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.CountedTruckDto;

import java.util.List;

public interface TruckScanningService {

    List<CountedTruckDto> countParcelsInTrucks(String fileName);
}
