package ru.liga.truckapp2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.service.ParcelService;
import ru.liga.truckapp2.util.Stringifier;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
@ShellCommandGroup("Parcel commands")
public class ParcelShellController {

    private final ParcelService parcelService;
    private final Stringifier stringifier;

    @ShellMethod(key = "all-parcels")
    public String getAllParcels() {
        List<Parcel> parcels = parcelService.getAll();
        return stringifier.stringifyParcelsList(parcels);
    }


    @ShellMethod(key = "get-parcel")
    public String getParcel(
            @ShellOption String name
    ) {
        Parcel parcel = parcelService.getByName(name).orElse(null);
        return (parcel != null) ? stringifier.stringifyParcel(parcel) : "Parcel not found";

    }

    @ShellMethod(key = "create-parcel")
    public String createParcel(
            @ShellOption String name,
            @ShellOption(defaultValue = "") String shape,
            @ShellOption Character symbol
    ) {
        Parcel parcel = parcelService.createParcel(name, shape, symbol);
        return "Parcel created: \n" + stringifier.stringifyParcel(parcel);
    }

    @ShellMethod(key = "update-parcel")
    public String updateParcel(
            @ShellOption String name,
            @ShellOption(defaultValue = "") String newName,
            @ShellOption(defaultValue = "") String newShape,
            @ShellOption(defaultValue = " ") Character newSymbol
    ) {

        Parcel parcel = parcelService.updateParcel(name, newName, newShape, newSymbol);
        return "Parcel updated: \n" + stringifier.stringifyParcel(parcel);
    }

    @ShellMethod(key = "delete-parcel")
    public String deleteParcel(
            @ShellOption String name
    ) {
        boolean deleted = parcelService.deleteParcel(name);
        return deleted ? "Parcel deleted" : "Parcel not found";
    }

}
