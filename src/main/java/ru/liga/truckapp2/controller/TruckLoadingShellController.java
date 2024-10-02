package ru.liga.truckapp2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.service.ParcelReadingService;
import ru.liga.truckapp2.service.TruckService;
import ru.liga.truckapp2.util.Stringifier;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
@ShellCommandGroup("Truck loading commands")
public class TruckLoadingShellController {

    private final TruckService truckService;
    private final ParcelReadingService parcelReadingService;

    private final Stringifier stringifier;

    @ShellMethod(key = "load-trucks")
    public String loadTrucks(
            @ShellOption(defaultValue = "6") Integer width,
            @ShellOption(defaultValue = "6") Integer height,
            @ShellOption(defaultValue = "10") Integer quantity,
            @ShellOption PackagingAlgorithmType algorithm,
            @ShellOption Boolean parcelsFromFile,
            @ShellOption Boolean parcelsByForm,
            @ShellOption String parcelIn,
            @ShellOption String out
    ) {

        List<Truck> availableTrucks = truckService.createTrucks(width, height, quantity);
        List<Parcel> parcelsToLoad = parcelReadingService.readParcels(parcelsFromFile, parcelsByForm, parcelIn);
        List<LoadedTruckDto> loadedTrucks = truckService.loadParcelsToTrucks(
                availableTrucks,
                parcelsToLoad,
                algorithm,
                out
        );
        return stringifier.stringifyLoadedTrucks(loadedTrucks);

    }


    @ShellMethod(key = "load-trucks-customized")
    public String loadTrucksCustomized(
            @ShellOption PackagingAlgorithmType algorithm,
            @ShellOption Boolean truckShapesFromFile,
            @ShellOption Boolean parcelsFromFile,
            @ShellOption Boolean parcelsByForm,
            @ShellOption String parcelIn,
            @ShellOption String truckShapesIn,
            @ShellOption String out
    ) {

        List<Truck> availableTrucks = truckService.createTrucksCustomized(
                truckShapesFromFile,
                truckShapesIn
        );
        List<Parcel> parcelsToLoad = parcelReadingService.readParcels(parcelsFromFile, parcelsByForm, parcelIn);
        List<LoadedTruckDto> loadedTrucks = truckService.loadParcelsToTrucks(
                availableTrucks,
                parcelsToLoad,
                algorithm,
                out
        );
        return stringifier.stringifyLoadedTrucks(loadedTrucks);
    }



}
