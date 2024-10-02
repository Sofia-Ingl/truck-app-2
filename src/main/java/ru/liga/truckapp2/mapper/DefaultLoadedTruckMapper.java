package ru.liga.truckapp2.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.view.LoadedTruckView;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DefaultLoadedTruckMapper implements LoadedTruckMapper {

    private final ParcelMapper parcelMapper;

    @Override
    public LoadedTruckDto loadedTruckToDto(LoadedTruckView loadedTruckView) {
        List<ParcelDto> parcels = new ArrayList<>();
        for (Parcel parcel : loadedTruckView.getParcels()) {
            parcels.add(parcelMapper.parcelToDto(parcel));
        }
        int truckHeight = loadedTruckView.getTruck().getHeight();
        String[] back = new String[truckHeight];
        for (int i = 0; i < truckHeight; i++) {
            back[i] = String.valueOf(loadedTruckView.getTruck().getBack()[i]);
        }
        return new LoadedTruckDto(
                back,
                parcels
        );
    }
}
