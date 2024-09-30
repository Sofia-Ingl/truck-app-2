package ru.liga.truckapp2.util;

import org.springframework.stereotype.Component;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.model.inner.Coordinates;
import ru.liga.truckapp2.model.view.LoadedTruckView;

import java.util.*;

@Component("steadyTruckLoader")
public class SteadyTruckLoader implements TruckLoader {

    @Override
    public List<LoadedTruckView> loadTrucks(List<Parcel> parcels, List<Truck> trucksAvailable) {

        List<Parcel> parcelsSortedDesc = new ArrayList<>(parcels);
        parcelsSortedDesc.sort(Collections.reverseOrder(Parcel.volumeComparator.thenComparing(Parcel.heightComparator)));

        List<Truck> trucksSorted = new ArrayList<>(trucksAvailable);
        trucksSorted.sort(
                Collections.reverseOrder(Truck.volumeComparator.thenComparing(Truck::getHeight).thenComparing(Truck::getWidth))
        );
        if (trucksSorted.size() < parcels.size() && trucksSorted.size() > 1) {
            // do this to have two largest trucks on both ends
            Truck firstTruck = trucksSorted.remove(0);
            trucksSorted.add(firstTruck);
        }

        boolean isDirectPhase = true;

        Map<Truck, List<Parcel>> parcelsInEveryTruck = new HashMap<>();
        for (Truck truck : trucksAvailable) {
            parcelsInEveryTruck.put(truck, new ArrayList<>());
        }

        while (!parcelsSortedDesc.isEmpty()) {

            int trucksLoadedSteadilyInIteration = 0;
            int[] differencesWithMaxParcelSize = new int[trucksSorted.size()];

            Parcel iterationMaxParcel = parcelsSortedDesc.get(0);
            Arrays.fill(differencesWithMaxParcelSize, iterationMaxParcel.calculateVolume());


            if (isDirectPhase) {
//                log.debug("Processing direct initial packaging phase on iteration");
                trucksLoadedSteadilyInIteration += directPhase(
                        trucksSorted,
                        parcelsInEveryTruck,
                        parcelsSortedDesc,
                        differencesWithMaxParcelSize,
                        true
                );
            } else {
//                log.debug("Processing reversed initial packaging phase on iteration");
                trucksLoadedSteadilyInIteration += reversedPhase(
                        trucksSorted,
                        parcelsInEveryTruck,
                        parcelsSortedDesc,
                        differencesWithMaxParcelSize,
                        true
                );
            }

            while (trucksLoadedSteadilyInIteration < trucksSorted.size()
                    && !parcelsSortedDesc.isEmpty()) {

                if (!isDirectPhase) {
//                    log.debug("Processing reversed additional packaging phase on iteration");
                    trucksLoadedSteadilyInIteration += directPhase(
                            trucksSorted,
                            parcelsInEveryTruck,
                            parcelsSortedDesc,
                            differencesWithMaxParcelSize,
                            false
                    );
                } else {
//                    log.debug("Processing direct additional packaging phase on iteration");
                    trucksLoadedSteadilyInIteration += reversedPhase(
                            trucksSorted,
                            parcelsInEveryTruck,
                            parcelsSortedDesc,
                            differencesWithMaxParcelSize,
                            false
                    );
                }

            }

            isDirectPhase = !isDirectPhase;
//            log.debug("Phase direction changed. Now phase is {}", directPhase ? "direct" : "reversed");
        }


        return trucksAvailable.stream()
                .filter(
                        truck->!parcelsInEveryTruck.get(truck).isEmpty()
                ).map(truck -> new LoadedTruckView(
                        truck,
                        parcelsInEveryTruck.get(truck)
                )).toList();
    }


    private int directPhase(List<Truck> trucks,
                            Map<Truck, List<Parcel>> parcelsInEveryTruck,
                            List<Parcel> parcelsSorted,
                            int[] differencesWithMaxParcelSize,
                            boolean firstCycleInPhase) {

        int trucksLoadedSteadilyInIteration = 0;
        for (int i = 0; i < trucks.size(); i++) {

            boolean throwException = firstCycleInPhase && i == 0;
            boolean isFilledOnCurrentIter = loadNextParcelIntoTruck(
                    parcelsInEveryTruck,
                    parcelsSorted,
                    trucks.get(i),
                    i,
                    differencesWithMaxParcelSize,
                    throwException
            );

            if (isFilledOnCurrentIter) {
                trucksLoadedSteadilyInIteration++;
            }

            if (parcelsSorted.isEmpty()) break;

        }
        return trucksLoadedSteadilyInIteration;
    }


    private int reversedPhase(List<Truck> trucks,
                              Map<Truck, List<Parcel>> parcelsInEveryTruck,
                              List<Parcel> parcelsSorted,
                              int[] differencesWithMaxParcelSize,
                              boolean firstCycleInPhase) {

        int trucksLoadedSteadilyInIteration = 0;
        for (int i = trucks.size() - 1; i >= 0; i--) {

            boolean throwException = firstCycleInPhase && i == trucks.size() - 1;
            boolean isFilledOnCurrentIter = loadNextParcelIntoTruck(
                    parcelsInEveryTruck,
                    parcelsSorted,
                    trucks.get(i),
                    i,
                    differencesWithMaxParcelSize,
                    throwException
            );
            if (isFilledOnCurrentIter) {
                trucksLoadedSteadilyInIteration++;
            }

            if (parcelsSorted.isEmpty()) break;

        }
        return trucksLoadedSteadilyInIteration;
    }


    private boolean loadNextParcelIntoTruck(Map<Truck, List<Parcel>> parcelsInEveryTruck,
                                            List<Parcel> parcelsSorted,
                                            Truck truck,
                                            int truckIndex,
                                            int[] differencesWithMaxParcelSize,
                                            boolean throwException) {

//        log.debug("Working with truck: {}", Arrays.deepToString(truck.getBack()));

        // truck was already filled steadily on previous iterations; continue with other trucks
        if (differencesWithMaxParcelSize[truckIndex] == 0) return false;

        int volumeUpperBound = differencesWithMaxParcelSize[truckIndex];

        List<Parcel> suitableParcels = new ArrayList<>();
        for (Parcel parcel : parcelsSorted) {
            if (parcel.calculateVolume() <= volumeUpperBound) {
                suitableParcels.add(parcel);
            }
        }

        if (suitableParcels.isEmpty()) {
//            log.debug("Cannot fill truck better on current iteration: no suitable parcels available " +
//                    "-> truck is loaded steadily on current iteration");
            differencesWithMaxParcelSize[truckIndex] = 0;
            return true;
        }

        while (!suitableParcels.isEmpty()) {

            Parcel suitableParcel = suitableParcels.get(0);

            boolean success = tryLoadParcel(
                    suitableParcel,
                    parcelsInEveryTruck,
                    truck
            );

            if (success) {
                int loadedParcelVolume = suitableParcel.calculateVolume();
                differencesWithMaxParcelSize[truckIndex] -= loadedParcelVolume;
                parcelsSorted.remove(suitableParcel);

                return differencesWithMaxParcelSize[truckIndex] == 0;
            }

            suitableParcels.remove(suitableParcel);

        }

        if (throwException) {
            throw new AppException("Cannot load suitableParcels steadily; truck current state:\n"
                    + Arrays.deepToString(truck.getBack()) + "\n" + "volume trying to load: " + volumeUpperBound);

        }

        // "Cannot fill truck better on current iteration: all suitable parcels cannot be loaded"
        differencesWithMaxParcelSize[truckIndex] = 0;
        return true;
    }

    private boolean tryLoadParcel(Parcel suitableParcel,
                                  Map<Truck, List<Parcel>> parcelsInEveryTruck,
                                  Truck truck
    ) {

        Coordinates coordinatesToLoad = truck.findPlaceForParcel(suitableParcel);
        if (coordinatesToLoad.getX() == -1 || coordinatesToLoad.getY() == -1) {
            return false;
        }
        truck.loadParcelWithoutCheck(coordinatesToLoad.getX(), coordinatesToLoad.getY(), suitableParcel);
        parcelsInEveryTruck.get(truck).add(suitableParcel);
        return true;
    }


}

