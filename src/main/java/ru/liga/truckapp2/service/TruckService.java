package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.Truck;

import java.util.List;

public interface TruckService {

    List<Truck> createTrucks(Integer width, Integer height, Integer quantity);

    List<Truck> createTrucksCustomized(Boolean fromFile, String input);

    List<LoadedTruckDto> loadParcelsToTrucks(List<Truck> trucks,
                                             List<Parcel> parcels,
                                             PackagingAlgorithmType algorithm,
                                             String outputFile);

    List<CountedTruckDto> countParcelsInTrucks(String file);
}
