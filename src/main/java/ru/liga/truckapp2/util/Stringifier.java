package ru.liga.truckapp2.util;

import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.model.Parcel;

import javax.validation.constraints.NotNull;
import java.util.List;

@Component
public class Stringifier {

    public String stringifyParcelsList(@NotNull List<Parcel> parcels) {

        if (parcels.isEmpty()) {
            return "Parcels list is empty";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Parcels list:\n");
        for (Parcel parcel : parcels) {
            stringBuilder.append(stringifyParcel(parcel));
        }
        return stringBuilder.toString();

    }


    public String stringifyParcel(@NotNull Parcel parcel) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{").append("\n");
        stringBuilder.append("\t").append("Name: ").append(parcel.getName()).append("\n");
        stringBuilder.append("\t").append("Shape: ").append("\n");
        for (int i = 0; i < parcel.getShape().length; i++) {
            stringBuilder.append("\t").append(" ");
            for (int j = 0; j < parcel.getShape()[i].length; j++) {
                if (parcel.getShape()[i][j]) {
                    stringBuilder.append(parcel.getSymbol());
                } else {
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("\t").append("Symbol: ").append(parcel.getSymbol()).append("\n");
        stringBuilder.append("}").append("\n");

        return stringBuilder.toString();

    }


    public String stringifyCountedTrucks(List<CountedTruckDto> countedTrucks) {
        if (countedTrucks.isEmpty()) {
            return "Counted trucks list is empty";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Counted trucks list:\n");
        for (CountedTruckDto countedTruck : countedTrucks) {
            stringBuilder.append(stringifyCountedTruck(countedTruck));
        }
        return stringBuilder.toString();
    }

    public String stringifyCountedTruck(CountedTruckDto countedTruck) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{").append("\n");
        stringBuilder.append("Truck:").append("\n");
        for (String line : countedTruck.getBack()) {
            stringBuilder.append(" ")
                    .append("+")
                    .append(line)
                    .append("+")
                    .append("\n");
        }
        stringBuilder.append("+".repeat(countedTruck.getBack()[0].length() + 2)).append("\n");
        stringBuilder.append("Parcel quantity by name:").append("\n");
        for (String name : countedTruck.getParcelQuantityByName().keySet()) {
            stringBuilder.append(" ")
                    .append(name)
                    .append(": ")
                    .append(countedTruck.getParcelQuantityByName().get(name))
                    .append("\n");
        }
        stringBuilder.append("}").append("\n");
        return stringBuilder.toString();
    }
}
