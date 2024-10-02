package ru.liga.truckapp2.util;

import ru.liga.truckapp2.dto.ParcelTypeDto;


public interface InputDtoCreator {

    ParcelTypeDto makeUpdateDto(String newName,
                                String newShape,
                                Character newSymbol);

    ParcelTypeDto makeCreateDto(String name,
                                String shape,
                                Character symbol);
}
