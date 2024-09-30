package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.Parcel;

import java.util.List;

public interface ParcelReadingService {

    List<Parcel> readFromFile(Boolean byForm, String input);
    List<Parcel> readFromStringByName(String input);
}
