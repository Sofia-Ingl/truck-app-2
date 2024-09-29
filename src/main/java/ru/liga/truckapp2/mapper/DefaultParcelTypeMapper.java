package ru.liga.truckapp2.mapper;

import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;

@Component
public class DefaultParcelTypeMapper implements ParcelTypeMapper {

    public ParcelType dtoToParcelType(ParcelTypeDto dto) {
        // todo: deep copy
        return new ParcelType(dto.getName(), dto.getShape(), dto.getSymbol());
    }
}
