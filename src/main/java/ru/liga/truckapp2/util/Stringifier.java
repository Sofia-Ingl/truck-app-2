package ru.liga.truckapp2.util;

import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.model.ParcelType;

import java.util.List;

public interface Stringifier {

    /**
     *
     * @param parcelTypes список типов посылок
     * @return список типов посылок, приведенный к строчному виду
     */
    String stringifyParcelTypesList(List<ParcelType> parcelTypes);

    /**
     *
     * @param parcelType тип посылок
     * @return тип посылок, приведенный к строчному виду
     */
    String stringifyParcelType(ParcelType parcelType);

    /**
     *
     * @param countedTrucks список грузовиков с посчитанными посылками
     * @return список грузовиков с посчитанными посылками, приведенный к строчному виду
     */
    String stringifyCountedTrucks(List<CountedTruckDto> countedTrucks);

    /**
     *
     * @param countedTruck грузовик с посчитанными посылками
     * @return грузовик с посчитанными посылками, приведенный к строчному виду
     */
    String stringifyCountedTruck(CountedTruckDto countedTruck);

    /**
     *
     * @param loadedTrucks список загруженных грузовиков
     * @return список загруженных грузовиков, приведенный к строчному виду
     */
    String stringifyLoadedTrucks(List<LoadedTruckDto> loadedTrucks);
}
