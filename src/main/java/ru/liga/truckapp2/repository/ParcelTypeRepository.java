package ru.liga.truckapp2.repository;

import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;

import java.util.List;
import java.util.Optional;

public interface ParcelTypeRepository {

    /**
     *
     * @param name имя типа
     * @return Optional, содержащий тип, если он существует
     */
    Optional<ParcelType> findByName(String name);

    /**
     *
     * @return список всех типов
     */
    List<ParcelType> findAll();

    /**
     *
     * @param parcelType тип посылок
     * @return сохраненный тип
     */
    ParcelType save(ParcelTypeDto parcelType);

    /**
     *
     * @param name имя типа
     * @return удален ли нет
     */
    boolean deleteByName(String name);

    /**
     *
     * @param name имя
     * @param newData данные для изменения
     * @return обновленный тип
     */
    ParcelType updateByName(String name, ParcelTypeDto newData);

    /**
     *
     * @param shape форма
     * @param symbol символ
     * @return Optional, содержащий первый попавшийся тип, если он существует, с заданным символом и формой
     */
    Optional<ParcelType> findByShapeAndSymbol(boolean[][] shape, char symbol);
}
