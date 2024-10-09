package ru.liga.truckapp2.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.service.ParcelValidationService;
import ru.liga.truckapp2.service.TruckFileService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class JsonTruckFileService implements TruckFileService {

    private final ParcelValidationService parcelValidationService;
    private final Gson gson;

    @Override
    public List<LoadedTruckDto> readTrucks(String fileName) {

        try {
            log.debug("Reading trucks from file {}", fileName);
            String jsonLoadedTrucks = Files.readString(Paths.get(fileName));
            Type type = new TypeToken<List<LoadedTruckDto>>() {
            }.getType();
            List<LoadedTruckDto> trucks = gson.fromJson(jsonLoadedTrucks, type);

            for (LoadedTruckDto loadedTruck : trucks) {
                for (ParcelDto parcel : loadedTruck.getParcels()) {
                    if (!parcelValidationService.validateParcel(parcel)) {
                        throw new AppException("Invalid parcel: " + parcel + " in truck "
                                + Arrays.deepToString(loadedTruck.getBack()));
                    }
                }
            }

            return trucks;

        } catch (IOException e) {
            throw new JsonIOException(e);
        }
    }

    @Override
    public void writeTrucks(String fileName, List<LoadedTruckDto> trucks) {

        log.debug("Writing trucks to file {}", fileName);
        try {
            String jsonTrucks = gson.toJson(trucks);
            Files.writeString(Paths.get(fileName), jsonTrucks);
        } catch (IOException e) {
            throw new JsonIOException(e);
        }

    }

}
