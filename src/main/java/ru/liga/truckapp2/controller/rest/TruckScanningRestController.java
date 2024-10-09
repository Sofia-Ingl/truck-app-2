package ru.liga.truckapp2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.service.TempFilesService;
import ru.liga.truckapp2.service.TruckService;
import ru.liga.truckapp2.util.Base64Decoder;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/truck-app/truck-scanning")
public class TruckScanningRestController {

    private final TruckService truckService;
    private final TempFilesService tempFilesService;
    private final Base64Decoder base64Decoder;

    @PostMapping
    public ResponseEntity<List<CountedTruckDto>> scanTrucks(
            @RequestBody String fileContentInBase64) {
        String file = tempFilesService.createTempFileFromContent(
                base64Decoder.decodeString(fileContentInBase64)
        );
        List<CountedTruckDto> scannedTrucks = truckService.countParcelsInTrucks(file);
        tempFilesService.deleteFile(file);
        return ResponseEntity.ok().body(scannedTrucks);
    }

}
