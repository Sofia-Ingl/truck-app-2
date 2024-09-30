package ru.liga.truckapp2.mapper;

import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.ParcelType;

@Component
public class DefaultParcelMapper implements ParcelMapper {
    @Override
    public Parcel dtoToParcel(ParcelDto dto) {
        // todo: deep copy
        return new Parcel(
                new ParcelType(dto.getName(), dto.getShape(), dto.getSymbol())
        );
    }
}
