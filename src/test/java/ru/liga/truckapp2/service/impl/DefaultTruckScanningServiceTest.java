package ru.liga.truckapp2.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.ParcelType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = DefaultTruckScanningService.class)
@ActiveProfiles("test")
class DefaultTruckScanningServiceTest {

    @Autowired
    private DefaultTruckScanningService truckScanningService;

    @Test
    void countParcelsInTrucks() {

        List<LoadedTruckDto> loadedTrucks = new ArrayList<>();

        String[] back = new String[]{
                "ooo",
                "oko",
                "ooo"
        };
        ParcelType parcelTypeK = new ParcelType(
                "k",
                new boolean[][]{{true}},
                'k'
        );
        ParcelDto parcelK = new ParcelDto(
                parcelTypeK.getName(), parcelTypeK.getShape(), parcelTypeK.getSymbol()
        );

        ParcelType parcelTypeO = new ParcelType(
                "o",
                new boolean[][]{{true, true, true}, {true, false, true}, {true, true, true}},
                'o'
        );
        ParcelDto parcelO = new ParcelDto(
                parcelTypeO.getName(), parcelTypeO.getShape(), parcelTypeO.getSymbol()
        );

        loadedTrucks.add(new LoadedTruckDto(back, List.of(parcelK, parcelO)));

        List<CountedTruckDto> countedTrucks = truckScanningService.countParcelsInTrucks(
                loadedTrucks
        );

        assertThat(countedTrucks.size()).isEqualTo(1);
        Map<String, Long> quantities = countedTrucks.get(0).getParcelQuantityByName();
        assertThat(quantities.keySet().size()).isEqualTo(2);
        assertThat(quantities.get("o")).isEqualTo(1);
        assertThat(quantities.get("k")).isEqualTo(1);

    }
}