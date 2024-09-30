package ru.liga.truckapp2.util;

import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.model.view.LoadedTruckView;

import java.util.List;

public interface TruckLoader {

    List<LoadedTruckView> loadTrucks(List<Parcel> parcels,
                                     List<Truck> trucksAvailable);

}
