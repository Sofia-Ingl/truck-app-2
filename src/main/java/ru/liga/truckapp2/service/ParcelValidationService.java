package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.ParcelDto;

public interface ParcelValidationService {

    /**
     *
     * @param parcel посылка для валидации
     * @return валидна или нет (проверяется, существует ли такой тип в системе)
     */
    boolean validateParcel(ParcelDto parcel);

}
