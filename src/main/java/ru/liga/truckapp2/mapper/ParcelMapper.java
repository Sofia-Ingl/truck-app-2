package ru.liga.truckapp2.mapper;

import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.ParcelCreateDto;
import ru.liga.truckapp2.model.Parcel;

@Component
public class ParcelMapper {

    public Parcel createDtoToParcel(ParcelCreateDto createDto) {
        return new Parcel(createDto.getName(), createDto.getShape(), createDto.getSymbol());
    }
}
