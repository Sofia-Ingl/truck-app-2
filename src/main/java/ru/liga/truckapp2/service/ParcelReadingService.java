package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.ParcelDto;

import java.util.List;

public interface ParcelReadingService {

    List<ParcelDto> readFromFile(Boolean byForm, String input);
    List<ParcelDto> readFromStringByName(String input);
}
