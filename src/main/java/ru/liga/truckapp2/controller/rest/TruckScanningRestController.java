package ru.liga.truckapp2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.service.TempFilesService;
import ru.liga.truckapp2.service.TruckService;
import ru.liga.truckapp2.util.Stringifier;

import java.time.temporal.Temporal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/truck-app/truck-scanning")
public class TruckScanningRestController {

    private final TruckService truckService;
    private final TempFilesService tempFilesService;

    @PostMapping
    public ResponseEntity<List<CountedTruckDto>> scanTrucks(
            @RequestBody String fileContentInBase64
    ) {

        String file = tempFilesService.createTempFileFromContent(fileContentInBase64);
        List<CountedTruckDto> scannedTrucks = truckService.countParcelsInTrucks(file);
        tempFilesService.deleteFile(file);
        return ResponseEntity.ok().body(scannedTrucks);
    }

}
