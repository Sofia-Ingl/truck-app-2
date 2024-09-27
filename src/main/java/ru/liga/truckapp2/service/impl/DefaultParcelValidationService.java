package ru.liga.truckapp2.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.mapper.ParcelMapper;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.service.ParcelService;
import ru.liga.truckapp2.service.ParcelValidationService;

@Service
@RequiredArgsConstructor
public class DefaultParcelValidationService implements ParcelValidationService {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;

    @Override
    public boolean validateParcel(ParcelDto parcel) {
        Parcel p = parcelMapper.dtoToParcel(parcel);
        return parcelService.getAll().stream()
                .anyMatch(p::equals);

    }
}
