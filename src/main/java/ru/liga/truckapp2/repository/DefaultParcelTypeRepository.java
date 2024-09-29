package ru.liga.truckapp2.repository;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.mapper.ParcelTypeMapper;
import ru.liga.truckapp2.model.ParcelType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DefaultParcelTypeRepository implements ParcelTypeRepository {

    @Value("${parcel.storage}")
    private String parcelsStorage;

    private final Gson gson;
    private final ParcelTypeMapper parcelTypeMapper;

    private List<ParcelType> parcelTypes;

    @Override
    synchronized public Optional<ParcelType> findByName(String name) {
        return getAllParcelTypes().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
    }

    @Override
    synchronized public List<ParcelType> findAll() {
        return Collections.unmodifiableList(getAllParcelTypes());
    }

    @Override
    synchronized public ParcelType save(ParcelTypeDto parcel) {
        ParcelType parcelTypeToSave = parcelTypeMapper.dtoToParcelType(parcel);
        List<ParcelType> allParcelTypes = getAllParcelTypes();
        if (checkParcelAlreadyExists(parcelTypeToSave.getName(), allParcelTypes)) {
            throw new AppException("Parcel type with name " + parcelTypeToSave.getName() + " already exists");
        }
        allParcelTypes.add(parcelTypeToSave);
        flushParcelTypes(allParcelTypes);
        return parcelTypeToSave;
    }

    @Override
    synchronized public boolean deleteByName(String name) {
        List<ParcelType> allParcelTypes = getAllParcelTypes();
        if (!checkParcelAlreadyExists(name, allParcelTypes)) {
            return false;
        }
        allParcelTypes.removeIf(p -> p.getName().equals(name));
        flushParcelTypes(allParcelTypes);
        return true;
    }

    @Override
    synchronized public ParcelType updateByName(String name, ParcelTypeDto newData) {
        List<ParcelType> allParcelTypes = getAllParcelTypes();
        ParcelType parcelType = findByName(name)
                .orElseThrow(() -> new AppException("Parcel type with name " + name + " does not exist"));
        ParcelType modified = getParcelWithNewFields(parcelType, newData);

        allParcelTypes.removeIf(p -> p.getName().equals(name));
        allParcelTypes.add(modified);

        flushParcelTypes(allParcelTypes);
        return modified;
    }

    @Override
    synchronized public Optional<ParcelType> findByShapeAndSymbol(boolean[][] shape, char symbol) {
        return getAllParcelTypes().stream()
                .filter(parcelType ->
                        parcelType.getSymbol() == symbol
                )
                .filter(parcelType ->
                        Arrays.deepEquals(parcelType.getShape(), shape)
                )
                .findFirst();
    }

    private ParcelType getParcelWithNewFields(ParcelType parcelType, ParcelTypeDto newData) {
        String newName = (newData.getName() != null) ? newData.getName() : parcelType.getName();
        boolean[][] newShapeToCopy = (newData.getShape() != null) ? newData.getShape() : parcelType.getShape();
        boolean[][] newShape = new boolean[newShapeToCopy.length][newShapeToCopy[0].length];
        for (int i = 0; i < newShapeToCopy.length; i++) {
            newShape[i] = newShapeToCopy[i].clone();
        }
        char newSymbol = (newData.getSymbol() != null) ? newData.getSymbol() : parcelType.getSymbol();
        return new ParcelType(
                newName,
                newShape,
                newSymbol
        );

    }

    private boolean checkParcelAlreadyExists(String name, List<ParcelType> allParcelTypes) {
        return allParcelTypes.stream()
                .anyMatch(p -> p.getName().equals(name));
    }

    private List<ParcelType> getAllParcelTypes() {
        if (parcelTypes != null) {
            return parcelTypes;
        }
        try {
            String jsonParcels = Files.readString(Paths.get(parcelsStorage));
            Type type = new TypeToken<List<ParcelType>>() {
            }.getType();
            parcelTypes = gson.fromJson(jsonParcels, type);
            if (parcelTypes == null) {
                parcelTypes = new ArrayList<>();
            }
            return parcelTypes;
        } catch (IOException e) {
            throw new JsonIOException(e);
        }
    }

    private void flushParcelTypes(List<ParcelType> allParcelTypes) {
        try {
            String jsonParcels = gson.toJson(allParcelTypes);
            Files.writeString(Paths.get(parcelsStorage), jsonParcels);
        } catch (IOException e) {
            throw new JsonIOException(e);
        }
    }

}
