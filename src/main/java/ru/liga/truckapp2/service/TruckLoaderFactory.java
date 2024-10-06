package ru.liga.truckapp2.service;

import ru.liga.truckapp2.model.PackagingAlgorithmType;

public interface TruckLoaderFactory {

    void register(PackagingAlgorithmType packagingAlgorithmType, TruckLoaderService truckLoaderService);
    TruckLoaderService getTruckLoader(PackagingAlgorithmType packagingAlgorithmType);
}
