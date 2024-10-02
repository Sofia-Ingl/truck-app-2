package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.dto.LoadedTruckDto;

import java.util.List;

public interface TruckScanningService {

    /**
     *
     * @param loadedTrucks загруженные грузовики
     * @return грузовики с посчитанными посылками
     */
    List<CountedTruckDto> countParcelsInTrucks(List<LoadedTruckDto> loadedTrucks);
}
