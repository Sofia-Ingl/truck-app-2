package ru.liga.truckapp2.mapper;

import ru.liga.truckapp2.dto.ParcelTypeCreateDto;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;

import java.util.List;

public interface ParcelTypeMapper {

    /**
     * @param dto dto
     * @return тип посылок
     */
    ParcelType dtoToParcelType(ParcelTypeCreateDto dto);

    /**
     * @param parcelType тип посылок
     * @return dto
     */
    ParcelTypeDto parcelTypeToDto(ParcelType parcelType);

    /**
     * @param parcelTypes список типов посылок
     * @return список dto
     */
    List<ParcelTypeDto> parcelTypesToDtoList(List<ParcelType> parcelTypes);
}
