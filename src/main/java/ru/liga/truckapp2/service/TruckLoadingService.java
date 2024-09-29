package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.model.Truck;

import java.util.List;

public interface TruckLoadingService {

    List<LoadedTruckDto> loadTrucks(List<ParcelDto> parcels,
                                    List<Truck> trucksAvailable,
                                    PackagingAlgorithmType algorithm);
}
