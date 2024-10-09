package ru.liga.truckapp2.mapper;

import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;

import java.util.List;

@Component
public class DefaultParcelTypeMapper implements ParcelTypeMapper {

    public ParcelType dtoToParcelType(ParcelTypeDto dto) {
        return new ParcelType(dto.getName(), dto.getShape(), dto.getSymbol());
    }

    @Override
    public ParcelTypeDto parcelTypeToDto(ParcelType parcelType) {
        return new ParcelTypeDto(
                parcelType.getName(), parcelType.getShape(), parcelType.getSymbol()
        );
    }

    @Override
    public List<ParcelTypeDto> parcelTypesToDtoList(List<ParcelType> parcelTypes) {
        return parcelTypes.stream()
                .map(this::parcelTypeToDto)
                .toList();
    }
}
