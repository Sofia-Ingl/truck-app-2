package ru.liga.truckapp2.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.liga.truckapp2.ArrayMatcher;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.service.ParcelTypeService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;

class DefaultParcelReadingServiceTest {

    @Test
    void readParcelsFromString() {

        ParcelTypeService parcelTypeServiceMock = Mockito.mock(ParcelTypeService.class);

        ParcelType type1 = new ParcelType(
                "1",
                new boolean[][]{{true}},
                '1'
        );

        ParcelType type2 = new ParcelType(
                "2",
                new boolean[][]{{true,true}},
                '2'
        );

        Mockito.when(parcelTypeServiceMock.getByName(type1.getName())).thenReturn(Optional.of(type1));
        Mockito.when(parcelTypeServiceMock.getByName(type2.getName())).thenReturn(Optional.of(type2));

        Mockito.when(parcelTypeServiceMock.getByShapeAndSymbol(type1.getShape(), type1.getSymbol())).thenReturn(Optional.of(type1));
        Mockito.when(parcelTypeServiceMock.getByShapeAndSymbol(type2.getShape(), type2.getSymbol())).thenReturn(Optional.of(type2));

        DefaultParcelReadingService parcelReadingService = new DefaultParcelReadingService(
                parcelTypeServiceMock
        );

        List<Parcel> parcels = parcelReadingService.readParcels(
                false,
                false,
                "1,2,2,2"
        );

        assertThat(parcels.size()).isEqualTo(4);
        assertThat(parcels.get(0).getType()).isEqualTo(type1);
        assertThat(parcels.get(1).getType()).isEqualTo(type2);
        assertThat(parcels.get(2).getType()).isEqualTo(type2);
        assertThat(parcels.get(3).getType()).isEqualTo(type2);

    }


    @Test
    void readParcelsFromFileByNames() throws IOException {

        ParcelTypeService parcelTypeServiceMock = Mockito.mock(ParcelTypeService.class);

        ParcelType type1 = new ParcelType(
                "1",
                new boolean[][]{{true}},
                '1'
        );

        ParcelType type2 = new ParcelType(
                "2",
                new boolean[][]{{true,true}},
                '2'
        );

        Mockito.when(parcelTypeServiceMock.getByName(type1.getName())).thenReturn(Optional.of(type1));
        Mockito.when(parcelTypeServiceMock.getByName(type2.getName())).thenReturn(Optional.of(type2));

        Mockito.when(parcelTypeServiceMock.getByShapeAndSymbol(type1.getShape(), type1.getSymbol())).thenReturn(Optional.of(type1));
        Mockito.when(parcelTypeServiceMock.getByShapeAndSymbol(type2.getShape(), type2.getSymbol())).thenReturn(Optional.of(type2));

        DefaultParcelReadingService parcelReadingService = new DefaultParcelReadingService(
                parcelTypeServiceMock
        );


        String fileName = "src\\test\\resources\\parcel-reading-service-test.txt";
        Path path = Path.of(fileName);
        String preparedParcelTypes = """
                1,2,2
                1
                """;
        Files.writeString(path, preparedParcelTypes);

        List<Parcel> parcels = parcelReadingService.readParcels(
                true,
                false,
                fileName
        );

        assertThat(parcels.size()).isEqualTo(4);
        assertThat(parcels.get(0).getType()).isEqualTo(type1);
        assertThat(parcels.get(1).getType()).isEqualTo(type2);
        assertThat(parcels.get(2).getType()).isEqualTo(type2);
        assertThat(parcels.get(3).getType()).isEqualTo(type1);

    }


    @Test
    void readParcelsFromFileByShapes() throws IOException {

        ParcelTypeService parcelTypeServiceMock = Mockito.mock(ParcelTypeService.class);

        ParcelType type1 = new ParcelType(
                "1",
                new boolean[][]{{true}},
                '1'
        );

        ParcelType type2 = new ParcelType(
                "2",
                new boolean[][]{{true,true}},
                '2'
        );

        Mockito.when(parcelTypeServiceMock.getByName(type1.getName())).thenReturn(Optional.of(type1));
        Mockito.when(parcelTypeServiceMock.getByName(type2.getName())).thenReturn(Optional.of(type2));

        Mockito.when(parcelTypeServiceMock.getByShapeAndSymbol(
                argThat(new ArrayMatcher(type1.getShape())),
                eq(type1.getSymbol()))
        ).thenReturn(Optional.of(type1));

        Mockito.when(parcelTypeServiceMock.getByShapeAndSymbol(
                argThat(new ArrayMatcher(type2.getShape())),
                eq(type2.getSymbol()))
        ).thenReturn(Optional.of(type2));

        DefaultParcelReadingService parcelReadingService = new DefaultParcelReadingService(
                parcelTypeServiceMock
        );


        String fileName = "src\\test\\resources\\parcel-reading-service-test.txt";
        Path path = Path.of(fileName);
        String preparedParcelTypes = """
                1
                
                22
                
                22
                
                1
                """;
        Files.writeString(path, preparedParcelTypes);

        List<Parcel> parcels = parcelReadingService.readParcels(
                true,
                true,
                fileName
        );

        assertThat(parcels.size()).isEqualTo(4);
        assertThat(parcels.get(0).getType()).isEqualTo(type1);
        assertThat(parcels.get(1).getType()).isEqualTo(type2);
        assertThat(parcels.get(2).getType()).isEqualTo(type2);
        assertThat(parcels.get(3).getType()).isEqualTo(type1);

    }



}