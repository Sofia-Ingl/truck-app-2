package ru.liga.truckapp2.util;

import ru.liga.truckapp2.dto.ParcelTypeCreateDto;
import ru.liga.truckapp2.dto.ParcelTypeDto;


public interface InputDtoCreator {

    /**
     * @param newName   имя
     * @param newShape  форма
     * @param newSymbol символ
     * @return dto
     */
    ParcelTypeDto makeUpdateDto(String newName,
                                String newShape,
                                Character newSymbol);

    /**
     * @param name   имя
     * @param shape  форма
     * @param symbol символ
     * @return dto
     */
    ParcelTypeCreateDto makeCreateDto(String name,
                                      String shape,
                                      Character symbol);
}
