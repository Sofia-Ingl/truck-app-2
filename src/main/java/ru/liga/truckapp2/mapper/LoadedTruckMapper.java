package ru.liga.truckapp2.mapper;

import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.model.view.LoadedTruckView;

public interface LoadedTruckMapper {

    LoadedTruckDto loadedTruckToDto(LoadedTruckView loadedTruckView);

}
