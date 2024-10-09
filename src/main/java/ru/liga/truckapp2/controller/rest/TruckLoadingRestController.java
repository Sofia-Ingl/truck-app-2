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
import ru.liga.truckapp2.service.impl.RestDtoTransformationService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/truck-app/truck-loading")
public class TruckLoadingRestController {

    private final TruckService truckService;
    private final RestDtoTransformationService restDtoTransformationService;

    @PostMapping("/default")
    public ResponseEntity<List<LoadedTruckDto>> loadTrucks(
            @RequestBody @Valid DefaultLoadingTaskDto loadingTaskDto) {
        List<LoadedTruckDto> loadedTrucks = truckService.loadParcels(restDtoTransformationService.transform(loadingTaskDto));
        return ResponseEntity.ok().body(loadedTrucks);
    }


    @PostMapping("/customized")
    public ResponseEntity<List<LoadedTruckDto>> loadTrucksCustomized(
            @RequestBody @Valid CustomizedLoadingTaskDto loadingTaskDto) {
        List<LoadedTruckDto> loadedTrucks = truckService.loadParcels(restDtoTransformationService.transform(loadingTaskDto));
        return ResponseEntity.ok().body(loadedTrucks);
    }

}
