package ru.liga.truckapp2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.service.TruckService;
import ru.liga.truckapp2.util.Stringifier;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
@ShellCommandGroup("Truck scanning commands")
public class TruckScanningShellController {

    private final TruckService truckService;
    private final Stringifier stringifier;

    /**
     *
     * @param file путь ко входному файлу
     * @return грузовики с подсчитанными посылками
     */
    @ShellMethod(key = "scan-trucks")
    public String scanTrucks(
            @ShellOption String file
    ) {
        List<CountedTruckDto> scannedTrucks = truckService.countParcelsInTrucks(file);
        return stringifier.stringifyCountedTrucks(scannedTrucks);
    }


}
