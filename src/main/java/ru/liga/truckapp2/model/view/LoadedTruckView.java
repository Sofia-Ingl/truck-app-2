package ru.liga.truckapp2.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.Truck;

import java.util.List;

@Getter
@AllArgsConstructor
public class LoadedTruckView {

    private Truck truck;
    private List<Parcel> parcels;

    @Override
    public String toString() {
        return "LoadedTruckView{" +
                "truck=" + truck +
                ", parcels=" + parcels +
                '}';
    }
}
