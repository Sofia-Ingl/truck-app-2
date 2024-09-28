package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.Parcel;

import java.util.List;
import java.util.Optional;

public interface ParcelService {

    List<Parcel> getAll();

    Optional<Parcel> getByName(String name);

    Parcel createParcel(ParcelDto createDto);

    Parcel updateParcel(String name,
                               ParcelDto updateDto);

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
