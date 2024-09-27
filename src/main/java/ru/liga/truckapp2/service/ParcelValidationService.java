package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.ParcelDto;

public interface ParcelValidationService {

    boolean validateParcel(ParcelDto parcel);

}
