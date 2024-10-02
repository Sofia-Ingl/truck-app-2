package ru.liga.truckapp2.util;

import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.model.view.LoadedTruckView;

import java.util.List;

public interface TruckLoader {

    /**
     * Заполнение грузовиков посылками
     *
     * @param parcels посылки
     * @param trucksAvailable грузовики
     * @return список заполненных грузовиков
     */
    List<LoadedTruckView> loadTrucks(List<Parcel> parcels,
                                     List<Truck> trucksAvailable);

    /**
     *
     * @return название алгоритма
     */
    PackagingAlgorithmType getAlgorithmType();

}
