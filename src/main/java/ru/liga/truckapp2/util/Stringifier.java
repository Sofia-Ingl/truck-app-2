package ru.liga.truckapp2.util;

import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.model.ParcelType;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface Stringifier {

    /**
     *
     * @param parcelTypes список типов посылок
     * @return список типов посылок, приведенный к строчному виду
     */
    String stringifyParcelTypesList(@NotNull List<ParcelType> parcelTypes);

    /**
     *
     * @param parcelType тип посылок
     * @return тип посылок, приведенный к строчному виду
     */
    String stringifyParcelType(@NotNull ParcelType parcelType);

    /**
     *
     * @param countedTrucks список грузовиков с посчитанными посылками
     * @return список грузовиков с посчитанными посылками, приведенный к строчному виду
     */
    String stringifyCountedTrucks(@NotNull List<CountedTruckDto> countedTrucks);

    /**
     *
     * @param countedTruck грузовик с посчитанными посылками
     * @return грузовик с посчитанными посылками, приведенный к строчному виду
     */
    String stringifyCountedTruck(@NotNull CountedTruckDto countedTruck);

    /**
     *
     * @param loadedTrucks список загруженных грузовиков
     * @return список загруженных грузовиков, приведенный к строчному виду
     */
    String stringifyLoadedTrucks(List<LoadedTruckDto> loadedTrucks);
}
