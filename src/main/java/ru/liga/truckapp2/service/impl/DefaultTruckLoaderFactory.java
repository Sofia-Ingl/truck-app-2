package ru.liga.truckapp2.service.impl;

import org.springframework.stereotype.Service;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.service.TruckLoaderFactory;
import ru.liga.truckapp2.service.TruckLoaderService;

import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultTruckLoaderFactory implements TruckLoaderFactory {

    private final Map<PackagingAlgorithmType, TruckLoaderService> truckLoaders;

    public DefaultTruckLoaderFactory() {
        truckLoaders = new HashMap<>();
    }

    @Override
    public void register(PackagingAlgorithmType packagingAlgorithmType, TruckLoaderService truckLoaderService) {
        truckLoaders.put(packagingAlgorithmType, truckLoaderService);
    }

    @Override
    public TruckLoaderService getTruckLoader(PackagingAlgorithmType packagingAlgorithmType) {
        return truckLoaders.get(packagingAlgorithmType);
    }
}
