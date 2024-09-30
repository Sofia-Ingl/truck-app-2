package ru.liga.truckapp2.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.mapper.LoadedTruckMapper;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.model.view.LoadedTruckView;
import ru.liga.truckapp2.service.TruckLoadingService;
import ru.liga.truckapp2.util.TruckLoader;

import java.util.List;

@Service
public class DefaultTruckLoadingService implements TruckLoadingService {

    private final TruckLoader optimizedTruckLoader;
    private final TruckLoader steadyTruckLoader;
    private final LoadedTruckMapper loadedTruckMapper;

    public DefaultTruckLoadingService(@Qualifier("optimizedTruckLoader") TruckLoader optimizedTruckLoader,
                                      @Qualifier("steadyTruckLoader") TruckLoader steadyTruckLoader,
                                      LoadedTruckMapper loadedTruckMapper) {
        this.optimizedTruckLoader = optimizedTruckLoader;
        this.steadyTruckLoader = steadyTruckLoader;
        this.loadedTruckMapper = loadedTruckMapper;
    }

    @Override
    public List<LoadedTruckDto> loadTrucks(List<Parcel> parcels,
                                           List<Truck> trucksAvailable,
                                           PackagingAlgorithmType algorithm) {
        List<LoadedTruckView> loadedTruckViews = switch (algorithm) {
            case STEADY -> steadyTruckLoader.loadTrucks(parcels, trucksAvailable);
            case OPTIMIZED -> optimizedTruckLoader.loadTrucks(parcels, trucksAvailable);
        };
        return loadedTruckViews.stream()
                .map(loadedTruckMapper::loadedTruckToDto)
                .toList();
    }

}
