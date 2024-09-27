package ru.liga.truckapp2.repository;

import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.Parcel;

import java.util.List;
import java.util.Optional;

public interface ParcelRepository {

    Optional<Parcel> findByName(String name);

    List<Parcel> findAll();

    Parcel save(ParcelDto parcel);

    boolean deleteByName(String name);

    Parcel updateByName(String name, ParcelDto newData);


}
