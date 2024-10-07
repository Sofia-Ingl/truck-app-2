package ru.liga.truckapp2.service;

import ru.liga.truckapp2.model.PackagingAlgorithmType;

public interface TruckLoaderFactory {

    TruckLoaderService getTruckLoader(PackagingAlgorithmType packagingAlgorithmType);
}
