package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.LoadedTruckDto;

import java.util.List;

public interface TruckFileService {

    /**
     *
     * @param fileName имя файла с грузовиками
     * @return список загруженных грузовиков
     */
    List<LoadedTruckDto> readTrucks(String fileName);

    /**
     *
     * @param fileName имя файла для записи
     * @param trucks список загруженных грузовиков
     */
    void writeTrucks(String fileName, List<LoadedTruckDto> trucks);

}
