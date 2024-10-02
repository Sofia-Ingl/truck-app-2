package ru.liga.truckapp2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.service.ParcelTypeService;
import ru.liga.truckapp2.util.InputDtoCreator;
import ru.liga.truckapp2.util.Stringifier;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@ShellComponent
@ShellCommandGroup("Parcel type commands")
public class ParcelTypeShellController {

    private final ParcelTypeService parcelTypeService;
    private final Stringifier stringifier;
    private final InputDtoCreator inputDtoCreator;

    /**
     *
     * @return список всех типов посылок, преобразованный в строку
     */
    @ShellMethod(key = "all-parcel-types")
    public String getAllParcelTypes() {
        List<ParcelType> parcelTypes = parcelTypeService.getAll();
        return stringifier.stringifyParcelTypesList(parcelTypes);
    }

    /**
     *
     * @param name название типа посылок
     * @return тип посылки с заданным именем
     */
    @ShellMethod(key = "get-parcel-type")
    public String getParcelType(
            @ShellOption String name
    ) {
        ParcelType parcelType = parcelTypeService.getByName(name).orElse(null);
        return (parcelType != null) ? stringifier.stringifyParcelType(parcelType) : "Parcel type not found";

    }

    /**
     *
     * @param name имя нового типа
     * @param shape форма, где строки отделены друг от друга запятыми, например "kk,kk, k"
     * @param symbol символ
     * @return созданный тип
     */
    @ShellMethod(key = "create-parcel-type")
    public String createParcelType(
            @ShellOption String name,
            @ShellOption String shape,
            @ShellOption Character symbol
    ) {
        ParcelTypeDto createDto = inputDtoCreator.makeCreateDto(name, shape, symbol);
        ParcelType parcelType = parcelTypeService.create(createDto);
        return "Parcel type created: \n" + stringifier.stringifyParcelType(parcelType);
    }

    /**
     *
     * @param name имя типа
     * @param newName новое имя (опционально)
     * @param newShape новая форма (опционально)
     * @param newSymbol новый символ (опционально)
     * @return обновленный тип
     */
    @ShellMethod(key = "update-parcel-type")
    public String updateParcelType(
            @ShellOption String name,
            @ShellOption(defaultValue = "") String newName,
            @ShellOption(defaultValue = "") String newShape,
            @ShellOption(defaultValue = " ") Character newSymbol
    ) {

        ParcelTypeDto updateDto = inputDtoCreator.makeUpdateDto(newName, newShape, newSymbol);
        ParcelType parcelType = parcelTypeService.update(name, updateDto);
        return "Parcel type updated: \n" + stringifier.stringifyParcelType(parcelType);
    }

    /**
     *
     * @param name имя типа
     * @return сообщение об успехе или неудаче операции (неудача происходит, если тип не найден)
     */
    @ShellMethod(key = "delete-parcel-type")
    public String deleteParcelType(
            @ShellOption String name
    ) {
        boolean deleted = parcelTypeService.delete(name);
        return deleted ? "Parcel type deleted" : "Parcel type not found";
    }

}
