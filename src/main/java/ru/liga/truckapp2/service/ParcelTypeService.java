package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.ParcelTypeCreateDto;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;

import java.util.List;
import java.util.Optional;

public interface ParcelTypeService {

    /**
     *
     * @return все существующие типы посылок
     */
    List<ParcelType> getAll();

    /**
     *
     * @param name имя типа
     * @return Optional, содержащий тип, если он существует
     */
    Optional<ParcelType> getByName(String name);

    /**
     *
     * @param shape форма
     * @param symbol символ
     * @return Optional, содержащий первый попавшийся тип, если такой существует, с заданным символом и формой
     */
    Optional<ParcelType> getByShapeAndSymbol(boolean[][] shape, char symbol);

    /**
     *
     * @param createDto тип посылок, который надо создать
     * @return созданный тип
     */
    ParcelType create(ParcelTypeCreateDto createDto);

    /**
     *
     * @param name имя
     * @param updateDto данные для изменения
     * @return обновленный тип
     */
    ParcelType update(String name,
                      ParcelTypeDto updateDto);

    /**
     *
     * @param name имя типа
     * @return удален ли нет
     */
    boolean delete(String name);


}
