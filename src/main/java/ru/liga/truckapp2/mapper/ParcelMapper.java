package ru.liga.truckapp2.mapper;

import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.Parcel;

public interface ParcelMapper {

    /**
     * @param dto dto
     * @return посылка
     */
    Parcel dtoToParcel(ParcelDto dto);

    /**
     * @param parcel посылка
     * @return dto
     */
    ParcelDto parcelToDto(Parcel parcel);
}
