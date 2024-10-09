package ru.liga.truckapp2.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.service.TruckLoaderFactory;
import ru.liga.truckapp2.service.TruckLoaderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DefaultTruckLoaderFactory implements TruckLoaderFactory {

    private final Map<PackagingAlgorithmType, TruckLoaderService> truckLoaders;

    public DefaultTruckLoaderFactory(List<TruckLoaderService> truckLoaderServices) {
        truckLoaders = new HashMap<>();
        for (TruckLoaderService truckLoaderService : truckLoaderServices) {
            truckLoaders.put(truckLoaderService.getAlgorithmType(), truckLoaderService);
            log.info("Truck loader of type " + truckLoaderService.getAlgorithmType() + " has been registered");
        }
    }

    @Override
    public TruckLoaderService getTruckLoader(PackagingAlgorithmType packagingAlgorithmType) {
        return truckLoaders.get(packagingAlgorithmType);
    }
}
