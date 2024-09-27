package ru.liga.truckapp2.mapper;

import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.Parcel;

@Component
public class ParcelMapper {

    public Parcel dtoToParcel(ParcelDto dto) {
        return new Parcel(dto.getName(), dto.getShape(), dto.getSymbol());
    }
}
