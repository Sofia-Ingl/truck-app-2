package ru.liga.truckapp2.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.dto.SizeDto;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.service.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class DefaultTruckService implements TruckService {

    private final String SIZE_INNER_DELIMITER = "x";
    private final String SIZE_OUTER_DELIMITER = ",";

    private final TruckLoadingService truckLoadingService;
    private final TruckFileService truckFileService;
    private final TruckScanningService truckScanningService;
    private final ParcelReadingService parcelReadingService;


    @Override
    public List<CountedTruckDto> countParcelsInTrucks(String file) {
        List<LoadedTruckDto> loadedTrucks = truckFileService.readTrucks(file);
        log.info("Read {} trucks", loadedTrucks.size());
        return truckScanningService.countParcelsInTrucks(loadedTrucks);
    }

    @Override
    public List<LoadedTruckDto> loadParcels(Integer width, Integer height, Integer quantity, Boolean parcelsFromFile, Boolean parcelsByForm, String parcelIn, PackagingAlgorithmType algorithm, String out) {

        List<Truck> availableTrucks = createTrucks(width, height, quantity);
        log.debug("Available trucks: {}", availableTrucks);
        List<Parcel> parcelsToLoad = parcelReadingService.readParcels(parcelsFromFile, parcelsByForm, parcelIn);
        log.debug("Parcels to load: {}", parcelsToLoad);
        return loadParcelsToTrucks(
                availableTrucks,
                parcelsToLoad,
                algorithm,
                out
        );
    }

    @Override
    public List<LoadedTruckDto> loadParcelsWithTruckSizesCustomized(Boolean truckShapesFromFile, String truckShapesIn, Boolean parcelsFromFile, Boolean parcelsByForm, String parcelIn, PackagingAlgorithmType algorithm, String out) {

        List<Truck> availableTrucks = createTrucksCustomized(
                truckShapesFromFile,
                truckShapesIn
        );
        log.debug("Available trucks: {}", availableTrucks);
        List<Parcel> parcelsToLoad = parcelReadingService.readParcels(parcelsFromFile, parcelsByForm, parcelIn);
        log.debug("Parcels to load: {}", parcelsToLoad);
        return loadParcelsToTrucks(
                availableTrucks,
                parcelsToLoad,
                algorithm,
                out
        );
    }

    private List<Truck> createTrucks(Integer width, Integer height, Integer quantity) {
        List<Truck> trucks = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            trucks.add(new Truck(width, height));
        }
        return trucks;
    }


    private List<Truck> createTrucksCustomized(Boolean fromFile, String input) {
        String actualSizes = input;
        if (fromFile) {
            try {
                actualSizes = String.join(
                        SIZE_OUTER_DELIMITER,
                        Files.readAllLines(Path.of(input)).stream()
                                .filter(l -> !l.isEmpty())
                                .toList()
                );
            } catch (IOException e) {
                throw new AppException("IOException occurred while reading truck sizes file '" +
                        input + "': " + e.getMessage());
            }
        }
        log.info("Creating trucks with sizes: " + actualSizes);
        List<SizeDto> sizes = getSizesFromString(actualSizes);
        List<Truck> trucks = new ArrayList<>();
        for (SizeDto size : sizes) {
            trucks.add(new Truck(size.getWidth(), size.getHeight()));
        }
        return trucks;
    }


    private List<LoadedTruckDto> loadParcelsToTrucks(List<Truck> trucks,
                                                    List<Parcel> parcels,
                                                    PackagingAlgorithmType algorithm,
                                                    String outputFile) {
        List<LoadedTruckDto> loadedTrucks = truckLoadingService.loadTrucks(
                parcels,
                trucks,
                algorithm
        );
        log.info("Loaded {} trucks", loadedTrucks.size());
        log.debug("Loaded trucks: {}", loadedTrucks);
        truckFileService.writeTrucks(outputFile, loadedTrucks);
        log.info("Wrote trucks to file '{}'", outputFile);
        return loadedTrucks;
    }


    private List<SizeDto> getSizesFromString(String input) {
        return Arrays.stream(input.split(SIZE_OUTER_DELIMITER))
                .map(String::trim)
                .map(sz -> sz.split(SIZE_INNER_DELIMITER))
                .map(sz -> new SizeDto(
                        Integer.valueOf(sz[0]),
                        Integer.valueOf(sz[1]))
                )
                .toList();
    }
}
