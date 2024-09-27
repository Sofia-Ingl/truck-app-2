package ru.liga.truckapp2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.service.TruckScanningService;
import ru.liga.truckapp2.util.Stringifier;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
@ShellCommandGroup("Truck scanning commands")
public class TruckScanningShellController {

    private final TruckScanningService truckScanningService;
    private final Stringifier stringifier;

    @ShellMethod(key = "scan-trucks")
    public String scanTrucks(
            @ShellOption String file
    ) {
        List<CountedTruckDto> scannedTrucks = truckScanningService.countParcelsInTrucks(file);
        return stringifier.stringifyCountedTrucks(scannedTrucks);
    }


}
