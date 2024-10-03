package ru.liga.truckapp2.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.mapper.DefaultParcelMapper;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.service.ParcelTypeService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DefaultParcelValidationServiceTest {

    @Test
    void validateParcelValid() {

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

//        Mockito.when(parcelTypeServiceMock.getByName(type1.getName())).thenReturn(Optional.of(type1));
//        Mockito.when(parcelTypeServiceMock.getByName(type2.getName())).thenReturn(Optional.of(type2));

        Mockito.when(parcelTypeServiceMock.getAll()).thenReturn(List.of(type1, type2));

        DefaultParcelValidationService parcelValidationService = new DefaultParcelValidationService(
                parcelTypeServiceMock,
                new DefaultParcelMapper()
        );

        ParcelDto p1 = new ParcelDto(type1.getName(), type1.getShape(), type1.getSymbol());

        boolean valid = parcelValidationService.validateParcel(p1);
        assertThat(valid).isTrue();

    }


    @Test
    void validateParcelsInvalid() {

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

        DefaultParcelValidationService parcelValidationService = new DefaultParcelValidationService(
                parcelTypeServiceMock,
                new DefaultParcelMapper()
        );

        ParcelDto p1 = new ParcelDto("invalid name", type1.getShape(), type1.getSymbol());

        boolean valid = parcelValidationService.validateParcel(p1);
        assertThat(valid).isFalse();

        ParcelDto p2 = new ParcelDto(type1.getName(), new boolean[][]{{true,true,true}}, type1.getSymbol());
        valid = parcelValidationService.validateParcel(p2);
        assertThat(valid).isFalse();

        ParcelDto p3 = new ParcelDto(type1.getName(), type1.getShape(), '2');
        valid = parcelValidationService.validateParcel(p3);
        assertThat(valid).isFalse();

    }
}