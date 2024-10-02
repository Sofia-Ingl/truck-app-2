package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.Truck;

import java.util.List;

public interface TruckService {

    /**
     *
     * @param width ширина грузовика
     * @param height высота грузовика
     * @param quantity количество
     * @return созданные грузовики
     */
    List<Truck> createTrucks(Integer width, Integer height, Integer quantity);

    /**
     *
     * @param fromFile размеры берутся из файла или нет
     * @param input вход (имя файла или строка с размерами)
     * @return созданные грузовики
     */
    List<Truck> createTrucksCustomized(Boolean fromFile, String input);

    /**
     *
     * @param trucks свободные грузовики
     * @param parcels посылки для загрузки
     * @param algorithm алгоритм погрузки
     * @param outputFile файл для записи результата
     * @return загруженные грузовики
     */
    List<LoadedTruckDto> loadParcelsToTrucks(List<Truck> trucks,
                                             List<Parcel> parcels,
                                             PackagingAlgorithmType algorithm,
                                             String outputFile);

    /**
     *
     * @param file файл, откуда берутся загруженные грузовики
     * @return грузовики с подчитанными посылками
     */
    List<CountedTruckDto> countParcelsInTrucks(String file);
}
