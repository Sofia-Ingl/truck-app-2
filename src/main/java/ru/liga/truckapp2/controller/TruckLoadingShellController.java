package ru.liga.truckapp2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RequiredArgsConstructor
@ShellComponent
@ShellCommandGroup("Truck loading commands")
public class TruckLoadingShellController {

    private final TruckService truckService;
    private final ParcelReadingService parcelReadingService;

    private final Stringifier stringifier;

    /**
     *
     * @param width ширина грузовика
     * @param height высота грузовика
     * @param quantity количество грузовиков
     * @param algorithm алгоритм погрузки
     * @param parcelsFromFile посылки берутся из файла или нет
     * @param parcelsByForm посылки считываются по форме или нет
     * @param parcelIn вход посылок (имя файла или строка с именами, трактуется в зависимости от арумента parcelsFromFile)
     * @param out файл для записи результата
     * @return список загруженных грузовиков
     */
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
        log.debug("Available trucks: {}", availableTrucks);
        List<Parcel> parcelsToLoad = parcelReadingService.readParcels(parcelsFromFile, parcelsByForm, parcelIn);
        log.debug("Parcels to load: {}", parcelsToLoad);
        List<LoadedTruckDto> loadedTrucks = truckService.loadParcelsToTrucks(
                availableTrucks,
                parcelsToLoad,
                algorithm,
                out
        );
        return stringifier.stringifyLoadedTrucks(loadedTrucks);

    }


    /**
     * Погрузка в грузовики кастомизированной формы
     *
     * @param algorithm алгоритм погрузки
     * @param truckShapesFromFile формы грузовиков лежат в файле или нет
     * @param parcelsFromFile посылки берутся из файла или нет
     * @param parcelsByForm посылки считываются по форме или нет
     * @param parcelIn вход посылок (имя файла или строка с именами, трактуется в зависимости от арумента parcelsFromFile)
     * @param truckShapesIn строка с формами грузовиков или имя файла, где она лежит
     * @param out файл для записи результата
     * @return список загруженных грузовиков
     */
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
        log.debug("Available trucks: {}", availableTrucks);
        List<Parcel> parcelsToLoad = parcelReadingService.readParcels(parcelsFromFile, parcelsByForm, parcelIn);
        log.debug("Parcels to load: {}", parcelsToLoad);
        List<LoadedTruckDto> loadedTrucks = truckService.loadParcelsToTrucks(
                availableTrucks,
                parcelsToLoad,
                algorithm,
                out
        );
        return stringifier.stringifyLoadedTrucks(loadedTrucks);
    }



}
