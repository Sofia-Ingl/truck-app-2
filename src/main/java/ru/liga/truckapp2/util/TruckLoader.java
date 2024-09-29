package ru.liga.truckapp2.util;

import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.Truck;

import java.util.List;

public interface TruckLoader {

    List<LoadedTruckDto> loadTrucks(List<ParcelDto> parcels,
                                    List<Truck> trucksAvailable);

}
