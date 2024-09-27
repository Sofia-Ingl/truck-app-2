package ru.liga.truckapp2.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.service.TruckFileService;
import ru.liga.truckapp2.service.TruckScanningService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DefaultTruckScanningService implements TruckScanningService {

    private final TruckFileService truckFileService;

    public List<CountedTruckDto> countParcelsInTrucks(String fileName) {
        return truckFileService.readTrucks(fileName)
                .stream()
                .map(this::countParcelsInTruck)
                .toList();
    }

    private CountedTruckDto countParcelsInTruck(LoadedTruckDto loadedTruck) {
        Map<String, Long> parcelQuantityByName = loadedTruck.getParcels()
                .stream()
                .collect(Collectors.groupingBy(
                        ParcelDto::getName,
                        Collectors.counting()
                ));
        return new CountedTruckDto(loadedTruck.getBack(), parcelQuantityByName);
    }

}
