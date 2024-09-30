package ru.liga.truckapp2.mapper;

import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.view.LoadedTruckView;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultLoadedTruckMapper implements LoadedTruckMapper {

    @Override
    public LoadedTruckDto loadedTruckToDto(LoadedTruckView loadedTruckView) {
        List<ParcelDto> parcels = new ArrayList<>();
        for (Parcel parcel : loadedTruckView.getParcels()) {
            int parcelWidth = parcel.getType().getShape()[0].length;
            int parcelHeight = parcel.getType().getShape().length;
            boolean[][] shape = new boolean[parcelHeight][parcelWidth];
            for (int i = 0; i < parcelHeight; i++) {
                System.arraycopy(parcel.getType().getShape()[i], 0, shape[i], 0, parcelWidth);
            }
            parcels.add(new ParcelDto(
                    parcel.getType().getName(),
                    shape,
                    parcel.getType().getSymbol()
            ));
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
