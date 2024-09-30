package ru.liga.truckapp2.util;

import org.springframework.stereotype.Component;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.model.inner.Coordinates;
import ru.liga.truckapp2.model.view.LoadedTruckView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("optimizedTruckLoader")
public class OptimizedTruckLoader implements TruckLoader {

    @Override
    public List<LoadedTruckView> loadTrucks(List<Parcel> parcels, List<Truck> trucksAvailable) {

        List<Parcel> parcelsSorted = new ArrayList<>(parcels);
        parcelsSorted.sort(Collections.reverseOrder(Parcel.widthComparator));
//        parcelsSorted.sort(Parcel.heightComparator);

        List<LoadedTruckView> loadedTrucks = new ArrayList<>();
        for (Truck truck : trucksAvailable) {

//            if (parcelsSorted.isEmpty()) {
//                break;
//            }

            List<Parcel> parcelsLoadedToCurrentTruck = new ArrayList<>();

            int nextParcelIdx = 0;
            do {
                 nextParcelIdx = loadSuitableParcel(
                         nextParcelIdx,
                         truck,
                         parcelsSorted,
                         parcelsLoadedToCurrentTruck
                 );
            } while (nextParcelIdx != -1 && !parcelsSorted.isEmpty());

            if (!parcelsLoadedToCurrentTruck.isEmpty()) {
                loadedTrucks.add(new LoadedTruckView(
                        truck,
                        parcelsLoadedToCurrentTruck
                ));
            }

        }

        if (!parcelsSorted.isEmpty()) {
            throw new AppException("Cannot pack all the parcels. Not enough trucks: " + trucksAvailable.size());
        }

        return loadedTrucks;
    }

    private int loadSuitableParcel(int nextParcelIdx,
                                       Truck truck,
                                       List<Parcel> parcelsSorted,
                                       List<Parcel> parcelsLoadedToCurrentTruck) {

        for (int i = nextParcelIdx; i < parcelsSorted.size(); i++) {

            // ищем наибольшую подходящую посылку
            // загружаем
            // посылку добавляем в parcels loaded
            // удаляем из parcels sorted

            Parcel currentParcel = parcelsSorted.get(i);
            Coordinates coordinates = truck.findPlaceForParcel(currentParcel);
            if (coordinates.getX() != -1 && coordinates.getY() != -1) {
                truck.loadParcelWithoutCheck(coordinates.getX(), coordinates.getY(), currentParcel);
                parcelsLoadedToCurrentTruck.add(currentParcel);
                parcelsSorted.remove(currentParcel);
                return i;
            }

        }

        return -1;

    }
}
