package ru.liga.truckapp2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.truckapp2.dto.CustomizedLoadingTaskDto;
import ru.liga.truckapp2.dto.DefaultLoadingTaskDto;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.service.TruckService;
import ru.liga.truckapp2.util.Base64Decoder;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/truck-app/truck-loading")
public class TruckLoadingRestController {

    private final TruckService truckService;
    private final Base64Decoder base64Decoder;

    @PostMapping("/default")
    public ResponseEntity<List<LoadedTruckDto>> loadTrucks(
            @RequestBody @Valid DefaultLoadingTaskDto loadingTaskDto
    ) {
        String actualInput = loadingTaskDto.getParcelIn();
        if (loadingTaskDto.getParcelsFromFile()) {
            actualInput = base64Decoder.decodeString(actualInput);
        }
        List<LoadedTruckDto> loadedTrucks = truckService.loadParcels(
                loadingTaskDto.getWidth(),
                loadingTaskDto.getHeight(),
                loadingTaskDto.getQuantity(),
                false,
                loadingTaskDto.getParcelsByForm(),
                actualInput,
                loadingTaskDto.getAlgorithm(),
                loadingTaskDto.getOut()
        );
        return ResponseEntity.ok().body(loadedTrucks);
    }


    @PostMapping("/customized")
    public ResponseEntity<List<LoadedTruckDto>> loadTrucksCustomized(
            @RequestBody @Valid CustomizedLoadingTaskDto loadingTaskDto
    ) {
        String actualParcelInput = loadingTaskDto.getParcelIn();
        if (loadingTaskDto.getParcelsFromFile()) {
            actualParcelInput = base64Decoder.decodeString(actualParcelInput);
        }

        String actualTruckShapesInput = loadingTaskDto.getTruckShapesIn();
        if (loadingTaskDto.getTruckShapesFromFile()) {
            actualTruckShapesInput = base64Decoder.decodeString(actualTruckShapesInput);
        }

        List<LoadedTruckDto> loadedTrucks = truckService.loadParcelsWithTruckSizesCustomized(
                false,
                actualTruckShapesInput,
                false,
                loadingTaskDto.getParcelsByForm(),
                actualParcelInput,
                loadingTaskDto.getAlgorithm(),
                loadingTaskDto.getOut()
        );

        return ResponseEntity.ok().body(loadedTrucks);
    }

}
