package ru.liga.truckapp2.util;

import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.model.ParcelType;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface Stringifier {

    String stringifyParcelTypesList(@NotNull List<ParcelType> parcelTypes);

    String stringifyParcelType(@NotNull ParcelType parcelType);

    String stringifyCountedTrucks(@NotNull List<CountedTruckDto> countedTrucks);

    String stringifyCountedTruck(@NotNull CountedTruckDto countedTruck);

    String stringifyLoadedTrucks(List<LoadedTruckDto> loadedTrucks);
}
