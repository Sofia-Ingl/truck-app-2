package ru.liga.truckapp2.mapper;

import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.ParcelType;

@Component
public class DefaultParcelMapper implements ParcelMapper {
    @Override
    public Parcel dtoToParcel(ParcelDto dto) {
        return new Parcel(
                new ParcelType(dto.getName(), dto.getShape(), dto.getSymbol())
        );
    }

    @Override
    public ParcelDto parcelToDto(Parcel parcel) {
        int parcelWidth = parcel.getType().getShape()[0].length;
        int parcelHeight = parcel.getType().getShape().length;
        boolean[][] shape = new boolean[parcelHeight][parcelWidth];
        for (int i = 0; i < parcelHeight; i++) {
            System.arraycopy(parcel.getType().getShape()[i], 0, shape[i], 0, parcelWidth);
        }
       return new ParcelDto(
                parcel.getType().getName(),
                shape,
                parcel.getType().getSymbol()
        );
    }
}
