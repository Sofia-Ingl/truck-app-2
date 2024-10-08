package ru.liga.truckapp2.service;

import ru.liga.truckapp2.model.PackagingAlgorithmType;

public interface TruckLoaderFactory {

    /**
     * @param packagingAlgorithmType тип алгоритма
     * @return сервис, выполняющий погрузку посылок по алгоритму заданного типа
     */
    TruckLoaderService getTruckLoader(PackagingAlgorithmType packagingAlgorithmType);
}
