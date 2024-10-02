package ru.liga.truckapp2.service;

import ru.liga.truckapp2.model.Parcel;

import java.util.List;

public interface ParcelReadingService {

    /**
     * Чтение посылок
     *
     * @param fromFile из файла или нет
     * @param byForm по форме или нет (если не из файла, то игнорируется)
     * @param input входная строка с именем файла или названиями посылок
     * @return список посылок
     */
    List<Parcel> readParcels(boolean fromFile, boolean byForm, String input);
}
