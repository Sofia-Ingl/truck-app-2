package ru.liga.truckapp2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.model.inner.Coordinates;
import ru.liga.truckapp2.model.view.LoadedTruckView;
import ru.liga.truckapp2.service.TruckLoaderService;
import ru.liga.truckapp2.service.TruckLoaderFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service("optimizedTruckLoader")
public class OptimizedTruckLoaderService implements TruckLoaderService {

    @Override
    public PackagingAlgorithmType getAlgorithmType() {
        return PackagingAlgorithmType.OPTIMIZED;
    }

    @Override
    public List<LoadedTruckView> loadTrucks(List<Parcel> parcels, List<Truck> trucksAvailable) {

        log.info("Loading trucks using optimized truck loader");
        List<Parcel> parcelsSorted = new ArrayList<>(parcels);
        parcelsSorted.sort(Collections.reverseOrder(Parcel.widthComparator.thenComparing(Parcel.volumeComparator)));
        log.debug("Parcels sorted: {}", parcelsSorted);

        List<LoadedTruckView> loadedTrucks = new ArrayList<>();
        for (Truck truck : trucksAvailable) {

            log.debug("Loading truck: {}", truck);
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
                log.debug("Truck added to truck list: {}", truck);
            }

        }

        if (!parcelsSorted.isEmpty()) {
            log.debug("Parcels left when packaging error occurred: {}", parcelsSorted);
            log.debug("Trucks loaded: {}", loadedTrucks);
            throw new AppException("Cannot pack all the parcels. Not enough trucks: " + trucksAvailable.size());
        }

        return loadedTrucks;
    }


    private int loadSuitableParcel(int nextParcelIdx,
                                       Truck truck,
                                       List<Parcel> parcelsSorted,
                                       List<Parcel> parcelsLoadedToCurrentTruck) {

        for (int i = nextParcelIdx; i < parcelsSorted.size(); i++) {

            Parcel currentParcel = parcelsSorted.get(i);
            log.debug("Trying to load parcel {} to truck {}", currentParcel, truck);
            Coordinates coordinates = truck.findPlaceForParcel(currentParcel);
            if (coordinates.getX() != -1 && coordinates.getY() != -1) {
                log.debug("Found place for current parcel: {}", coordinates);
                truck.loadParcelWithoutCheck(coordinates.getX(), coordinates.getY(), currentParcel);
                parcelsLoadedToCurrentTruck.add(currentParcel);
                parcelsSorted.remove(currentParcel);
                return i;
            }
            log.debug("Found no place for parcel {}", currentParcel);

        }

        return -1;

    }
}
