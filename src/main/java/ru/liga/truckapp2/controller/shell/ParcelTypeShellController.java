package ru.liga.truckapp2.controller.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.truckapp2.dto.ParcelTypeCreateDto;
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

    @ShellMethod(key = "all-parcel-types",
            value = "Возвращает список всех типов посылок, преобразованный в строку")
    public String getAllParcelTypes() {
        List<ParcelType> parcelTypes = parcelTypeService.getAll();
        return stringifier.stringifyParcelTypesList(parcelTypes);
    }


    @ShellMethod(key = "get-parcel-type",
    value = "Возвращает тип посылки с заданным именем")
    public String getParcelType(
            @ShellOption(help = "название типа посылок") String name
    ) {
        ParcelType parcelType = parcelTypeService.getByName(name).orElse(null);
        return (parcelType != null) ? stringifier.stringifyParcelType(parcelType) : "Parcel type not found";

    }

    @ShellMethod(key = "create-parcel-type",
            value = "Возвращает созданный тип")
    public String createParcelType(
            @ShellOption(help = "имя нового типа") String name,
            @ShellOption(help = "форма, где строки отделены друг от друга запятыми, например \"kk,kk, k\"") String shape,
            @ShellOption(help = "символ") Character symbol
    ) {
        ParcelTypeCreateDto createDto = inputDtoCreator.makeCreateDto(name, shape, symbol);
        ParcelType parcelType = parcelTypeService.create(createDto);
        return "Parcel type created: \n" + stringifier.stringifyParcelType(parcelType);
    }

    @ShellMethod(key = "update-parcel-type",
            value = "Возвращает обновленный тип")
    public String updateParcelType(
            @ShellOption(help = "имя типа") String name,
            @ShellOption(defaultValue = "", help = "новое имя") String newName,
            @ShellOption(defaultValue = "", help = "новая форма") String newShape,
            @ShellOption(defaultValue = " ", help = "новый символ") Character newSymbol
    ) {

        ParcelTypeDto updateDto = inputDtoCreator.makeUpdateDto(newName, newShape, newSymbol);
        ParcelType parcelType = parcelTypeService.update(name, updateDto);
        return "Parcel type updated: \n" + stringifier.stringifyParcelType(parcelType);
    }

    @ShellMethod(key = "delete-parcel-type",
            value = "Возвращает сообщение об успехе или неудаче операции (неудача происходит, если тип не найден)")
    public String deleteParcelType(
            @ShellOption(help = "имя типа") String name
    ) {
        boolean deleted = parcelTypeService.delete(name);
        return deleted ? "Parcel type deleted" : "Parcel type not found";
    }

}
