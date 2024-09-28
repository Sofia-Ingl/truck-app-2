package ru.liga.truckapp2.repository;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.mapper.ParcelMapper;
import ru.liga.truckapp2.model.Parcel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DefaultParcelRepository implements ParcelRepository {

    @Value("${parcel.storage}")
    private String parcelsStorage;

    private final Gson gson;
    private final ParcelMapper parcelMapper;

    private List<Parcel> parcels;

    synchronized public Optional<Parcel> findByName(String name) {
        return getAllParcels().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
    }

    synchronized public List<Parcel> findAll() {
        return Collections.unmodifiableList(getAllParcels());
    }

    synchronized public Parcel save(ParcelDto parcel) {
        Parcel parcelToSave = parcelMapper.dtoToParcel(parcel);
        List<Parcel> allParcels = getAllParcels();
        if (checkParcelAlreadyExists(parcelToSave.getName(), allParcels)) {
            throw new AppException("Parcel with name " + parcelToSave.getName() + " already exists");
        }
        allParcels.add(parcelToSave);
        flushParcels(allParcels);
        return parcelToSave;
    }

    synchronized public boolean deleteByName(String name) {
        List<Parcel> allParcels = getAllParcels();
        if (!checkParcelAlreadyExists(name, allParcels)) {
            return false;
        }
        allParcels.removeIf(p -> p.getName().equals(name));
        flushParcels(allParcels);
        return true;
    }

    synchronized public Parcel updateByName(String name, ParcelDto newData) {
        List<Parcel> allParcels = getAllParcels();
        Parcel parcel = findByName(name)
                .orElseThrow(() -> new AppException("Parcel with name " + name + " does not exist"));
        Parcel modified = getParcelWithNewFields(parcel, newData);

        allParcels.removeIf(p -> p.getName().equals(name));
        allParcels.add(modified);

        flushParcels(allParcels);
        return modified;
    }

    private Parcel getParcelWithNewFields(Parcel parcel, ParcelDto newData) {
        String newName = (newData.getName() != null) ? newData.getName() : parcel.getName();
        boolean[][] newShapeToCopy = (newData.getShape() != null) ? newData.getShape() : parcel.getShape();
        boolean[][] newShape = new boolean[newShapeToCopy.length][newShapeToCopy[0].length];
        for (int i = 0; i < newShapeToCopy.length; i++) {
            newShape[i] = newShapeToCopy[i].clone();
        }
        char newSymbol = (newData.getSymbol() != null) ? newData.getSymbol() : parcel.getSymbol();
        return new Parcel(
                newName,
                newShape,
                newSymbol
        );

    }

    private boolean checkParcelAlreadyExists(String name, List<Parcel> allParcels) {
        return allParcels.stream()
                .anyMatch(p -> p.getName().equals(name));
    }

    private List<Parcel> getAllParcels() {
        if (parcels != null) {
            return parcels;
        }
        try {
            String jsonParcels = Files.readString(Paths.get(parcelsStorage));
            Type type = new TypeToken<List<Parcel>>() {
            }.getType();
            parcels = gson.fromJson(jsonParcels, type);
            if (parcels == null) {
                parcels = new ArrayList<>();
            }
            return parcels;
        } catch (IOException e) {
            throw new JsonIOException(e);
        }
    }

    private void flushParcels(List<Parcel> allParcels) {
        try {
            String jsonParcels = gson.toJson(allParcels);
            Files.writeString(Paths.get(parcelsStorage), jsonParcels);
        } catch (IOException e) {
            throw new JsonIOException(e);
        }
    }

}
