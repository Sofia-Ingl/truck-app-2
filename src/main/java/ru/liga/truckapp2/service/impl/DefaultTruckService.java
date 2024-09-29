package ru.liga.truckapp2.service.impl;

import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.SizeDto;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.service.TruckService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DefaultTruckService implements TruckService {

    private final String SIZE_INNER_DELIMITER = "x";
    private final String SIZE_OUTER_DELIMITER = ",";

    @Override
    public List<Truck> createTrucks(Integer width, Integer height, Integer quantity) {
        List<Truck> trucks = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            trucks.add(new Truck(width, height));
        }
        return trucks;
    }

    @Override
    public List<Truck> createTrucksCustomized(Boolean fromFile, String input) {
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
        List<SizeDto> sizes = getSizesFromString(actualSizes);
        List<Truck> trucks = new ArrayList<>();
        for (SizeDto size : sizes) {
            trucks.add(new Truck(size.getWidth(), size.getHeight()));
        }
        return trucks;
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
