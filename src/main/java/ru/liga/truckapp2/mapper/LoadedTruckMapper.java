package ru.liga.truckapp2.mapper;

import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.model.view.LoadedTruckView;

public interface LoadedTruckMapper {

    /**
     *
     * @param loadedTruckView загруженный грузовик
     * @return dto
     */
    LoadedTruckDto loadedTruckToDto(LoadedTruckView loadedTruckView);

}
