package ru.liga.truckapp2.mapper;

import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.Parcel;

public interface ParcelMapper {

    Parcel dtoToParcelType(ParcelDto dto);
}
