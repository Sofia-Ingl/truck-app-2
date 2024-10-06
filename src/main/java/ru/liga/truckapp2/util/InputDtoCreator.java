package ru.liga.truckapp2.util;

import ru.liga.truckapp2.dto.ParcelTypeCreateDto;
import ru.liga.truckapp2.dto.ParcelTypeDto;


public interface InputDtoCreator {

    ParcelTypeDto makeUpdateDto(String newName,
                                String newShape,
                                Character newSymbol);

    ParcelTypeCreateDto makeCreateDto(String name,
                                      String shape,
                                      Character symbol);
}
