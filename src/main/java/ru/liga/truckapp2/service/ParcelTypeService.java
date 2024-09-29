package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;

import java.util.List;
import java.util.Optional;

public interface ParcelTypeService {

    List<ParcelType> getAll();

    Optional<ParcelType> getByName(String name);

    Optional<ParcelType> getByShapeAndSymbol(boolean[][] shape, char symbol);

    ParcelType createParcel(ParcelTypeDto createDto);

    ParcelType updateParcel(String name,
                            ParcelTypeDto updateDto);

    boolean deleteParcel(String name);


}
