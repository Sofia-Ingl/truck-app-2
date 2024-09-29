package ru.liga.truckapp2.service.impl;

import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.service.TruckLoadingService;

import java.util.List;

@Service
public class DefaultTruckLoadingService implements TruckLoadingService {

    @Override
    public List<LoadedTruckDto> loadTrucks(List<ParcelDto> parcels, List<Truck> trucksAvailable, PackagingAlgorithmType algorithm) {
        return List.of();
    }
}
