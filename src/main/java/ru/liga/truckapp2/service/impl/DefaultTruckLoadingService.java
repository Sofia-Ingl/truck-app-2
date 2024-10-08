package ru.liga.truckapp2.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.mapper.LoadedTruckMapper;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.model.view.LoadedTruckView;
import ru.liga.truckapp2.service.TruckLoadingService;
import ru.liga.truckapp2.util.TruckLoader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DefaultTruckLoadingService implements TruckLoadingService {

    private final Map<PackagingAlgorithmType, TruckLoader> truckLoaders;
    private final LoadedTruckMapper loadedTruckMapper;

    public DefaultTruckLoadingService(List<TruckLoader> truckLoaders,
                                      LoadedTruckMapper loadedTruckMapper) {

        this.truckLoaders = truckLoaders.stream().collect(
                Collectors.toMap(
                        TruckLoader::getAlgorithmType,
                        loader -> loader
                ));
        log.info("Truck loaders of types {} present in system", this.truckLoaders.keySet());
        this.loadedTruckMapper = loadedTruckMapper;
    }

    @Override
    public List<LoadedTruckDto> loadTrucks(List<Parcel> parcels,
                                           List<Truck> trucksAvailable,
                                           PackagingAlgorithmType algorithm) {

        TruckLoader truckLoader = truckLoaders.get(algorithm);
        if (truckLoader == null) {
            throw new AppException("No truck loader found for algorithm " + algorithm);
        }
        List<LoadedTruckView> loadedTruckViews = truckLoader.loadTrucks(parcels, trucksAvailable);
        return loadedTruckViews.stream()
                .map(loadedTruckMapper::loadedTruckToDto)
                .toList();
    }

}
