package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;

import java.util.List;
import java.util.Optional;

public interface ParcelTypeService {

    List<ParcelType> getAll();

    Optional<ParcelType> getByName(String name);

    ParcelType createParcel(ParcelTypeDto createDto);

    ParcelType updateParcel(String name,
                            ParcelTypeDto updateDto);

//    Parcel createParcel(String name,
//                               String shape,
//                               Character symbol);
//
//    Parcel updateParcel(String name,
//                               String newName,
//                               String newShape,
//                               Character newSymbol);

    boolean deleteParcel(String name);

}
