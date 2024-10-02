package ru.liga.truckapp2.mapper;

import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;

public interface ParcelTypeMapper {

    /**
     * @param dto dto
     * @return тип посылок
     */
    ParcelType dtoToParcelType(ParcelTypeDto dto);
}
