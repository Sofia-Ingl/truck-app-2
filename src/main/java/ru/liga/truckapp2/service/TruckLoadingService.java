package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.Truck;

import java.util.List;

public interface TruckLoadingService {

    /**
     * @param parcels         посылки для загрузки
     * @param trucksAvailable доступные грузовики
     * @param algorithm       алгоритм
     * @return список загруженных грузовиков
     */
    List<LoadedTruckDto> loadTrucks(List<Parcel> parcels,
                                    List<Truck> trucksAvailable,
                                    PackagingAlgorithmType algorithm);
}
