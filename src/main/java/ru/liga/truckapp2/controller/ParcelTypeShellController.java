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

    @ShellMethod(key = "all-parcel-types")
    public String getAllParcels() {
        List<ParcelType> parcelTypes = parcelTypeService.getAll();
        return stringifier.stringifyParcelTypesList(parcelTypes);
    }

    @ShellMethod(key = "get-parcel-type")
    public String getParcel(
            @ShellOption String name
    ) {
        ParcelType parcelType = parcelTypeService.getByName(name).orElse(null);
        return (parcelType != null) ? stringifier.stringifyParcelType(parcelType) : "Parcel type not found";

    }

    @ShellMethod(key = "create-parcel-type")
    public String createParcel(
            @ShellOption String name,
            @ShellOption String shape,
            @ShellOption Character symbol
    ) {
        ParcelTypeDto createDto = inputDtoCreator.makeCreateDto(name, shape, symbol);
        ParcelType parcelType = parcelTypeService.createParcel(createDto);
        return "Parcel type created: \n" + stringifier.stringifyParcelType(parcelType);
    }

    @ShellMethod(key = "update-parcel-type")
    public String updateParcel(
            @ShellOption String name,
            @ShellOption(defaultValue = "") String newName,
            @ShellOption(defaultValue = "") String newShape,
            @ShellOption(defaultValue = " ") Character newSymbol
    ) {

        ParcelTypeDto updateDto = inputDtoCreator.makeUpdateDto(newName, newShape, newSymbol);
        ParcelType parcelType = parcelTypeService.updateParcel(name, updateDto);
        return "Parcel type updated: \n" + stringifier.stringifyParcelType(parcelType);
    }

    @ShellMethod(key = "delete-parcel-type")
    public String deleteParcel(
            @ShellOption String name
    ) {
        boolean deleted = parcelTypeService.deleteParcel(name);
        return deleted ? "Parcel type deleted" : "Parcel type not found";
    }

}
