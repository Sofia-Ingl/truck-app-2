package ru.liga.truckapp2.service;

import ru.liga.truckapp2.model.Parcel;

import java.util.List;

public interface ParcelReadingService {

    List<Parcel> readParcels(boolean fromFile, boolean byForm, String input);
}
