package ru.liga.truckapp2.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.service.TruckFileService;
import ru.liga.truckapp2.service.TruckLoadingService;
import ru.liga.truckapp2.service.TruckScanningService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class DefaultTruckServiceTest {

    @Test
    void createTrucksCustomizedWhenSizesFromString() {

        TruckFileService truckFileServiceMock = Mockito.mock(TruckFileService.class);
        TruckLoadingService truckLoadingServiceMock = Mockito.mock(TruckLoadingService.class);
        TruckScanningService truckScanningServiceMock = Mockito.mock(TruckScanningService.class);

        DefaultTruckService truckService = new DefaultTruckService(
                truckLoadingServiceMock,
                truckFileServiceMock,
                truckScanningServiceMock
        );

        List<Truck> trucks = truckService.createTrucksCustomized(false, "2x2,3x10");
        assertThat(trucks.size()).isEqualTo(2);
        assertThat(trucks.get(0).getHeight()).isEqualTo(2);
        assertThat(trucks.get(0).getWidth()).isEqualTo(2);
        assertThat(trucks.get(1).getHeight()).isEqualTo(10);
        assertThat(trucks.get(1).getWidth()).isEqualTo(3);

    }

    @Test
    void createTrucksCustomizedWhenSizesFromFile() throws IOException {


        TruckFileService truckFileServiceMock = Mockito.mock(TruckFileService.class);
        TruckLoadingService truckLoadingServiceMock = Mockito.mock(TruckLoadingService.class);
        TruckScanningService truckScanningServiceMock = Mockito.mock(TruckScanningService.class);

        DefaultTruckService truckService = new DefaultTruckService(
                truckLoadingServiceMock,
                truckFileServiceMock,
                truckScanningServiceMock
        );

        String fileName = "src\\test\\resources\\truck-service-test.txt";
        Path path = Path.of(fileName);
        String preparedParcelTypes = """
                2x2, 3x10
                6x6
                """;
        Files.writeString(path, preparedParcelTypes);

        List<Truck> trucks = truckService.createTrucksCustomized(true, fileName);
        assertThat(trucks.size()).isEqualTo(3);
        assertThat(trucks.get(0).getHeight()).isEqualTo(2);
        assertThat(trucks.get(0).getWidth()).isEqualTo(2);
        assertThat(trucks.get(1).getHeight()).isEqualTo(10);
        assertThat(trucks.get(1).getWidth()).isEqualTo(3);
        assertThat(trucks.get(2).getHeight()).isEqualTo(6);
        assertThat(trucks.get(2).getWidth()).isEqualTo(6);


    }

}