package ru.liga.truckapp2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.mapper.LoadedTruckMapper;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.model.view.LoadedTruckView;
import ru.liga.truckapp2.service.TruckLoaderFactory;
import ru.liga.truckapp2.service.TruckLoaderService;
import ru.liga.truckapp2.service.TruckLoadingService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTruckLoadingService implements TruckLoadingService {

    //    private final Map<PackagingAlgorithmType, TruckLoader> truckLoaders;
    private final TruckLoaderFactory truckLoaderFactory;
    private final LoadedTruckMapper loadedTruckMapper;

//    public DefaultTruckLoadingService(List<TruckLoader> truckLoaders,
//                                      LoadedTruckMapper loadedTruckMapper) {
//
//        this.truckLoaders = truckLoaders.stream().collect(
//                Collectors.toMap(
//                        TruckLoader::getAlgorithmType,
//                        loader -> loader
//                ));
//        log.info("Truck loaders of types {} present in system", this.truckLoaders.keySet());
//        this.loadedTruckMapper = loadedTruckMapper;
//    }

    @Override
    public List<LoadedTruckDto> loadTrucks(List<Parcel> parcels,
                                           List<Truck> trucksAvailable,
                                           PackagingAlgorithmType algorithm) {

        TruckLoaderService truckLoaderService = truckLoaderFactory.getTruckLoader(algorithm);
        if (truckLoaderService == null) {
            throw new AppException("No truck loader found for algorithm " + algorithm);
        }
        List<LoadedTruckView> loadedTruckViews = truckLoaderService.loadTrucks(parcels, trucksAvailable);
        return loadedTruckViews.stream()
                .map(loadedTruckMapper::loadedTruckToDto)
                .toList();
    }

}
