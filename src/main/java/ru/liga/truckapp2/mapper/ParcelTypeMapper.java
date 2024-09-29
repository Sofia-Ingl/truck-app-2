package ru.liga.truckapp2.mapper;

import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;

public interface ParcelTypeMapper {

    ParcelType dtoToParcelType(ParcelTypeDto dto);
}
