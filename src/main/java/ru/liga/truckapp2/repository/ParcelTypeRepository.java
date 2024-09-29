package ru.liga.truckapp2.repository;

import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;

import java.util.List;
import java.util.Optional;

public interface ParcelTypeRepository {

    Optional<ParcelType> findByName(String name);

    List<ParcelType> findAll();

    ParcelType save(ParcelTypeDto parcel);

    boolean deleteByName(String name);

    ParcelType updateByName(String name, ParcelTypeDto newData);


}
