package ru.liga.truckapp2.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.mapper.ParcelMapper;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.service.ParcelTypeService;
import ru.liga.truckapp2.service.ParcelValidationService;

@Service
@RequiredArgsConstructor
public class DefaultParcelValidationService implements ParcelValidationService {

    private final ParcelTypeService parcelTypeService;
    private final ParcelMapper parcelMapper;

    @Override
    public boolean validateParcel(ParcelDto parcel) {
        ParcelType p = parcelMapper.dtoToParcelType(parcel).getType();
        return parcelTypeService.getAll().stream()
                .anyMatch(p::equals);

    }
}
