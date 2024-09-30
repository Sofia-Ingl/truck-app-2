package ru.liga.truckapp2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.service.ParcelReadingService;
import ru.liga.truckapp2.service.ParcelTypeService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultParcelReadingService implements ParcelReadingService {

    private final ParcelTypeService parcelTypeService;

    private final String PARCEL_NAME_DELIMITER = ",";

    @Override
    public List<Parcel> readFromFile(Boolean byForm, String input) {
        if (!byForm) {
            return readFromFileByName(input);
        }
        return readFromFileByForm(input);
    }

    @Override
    public List<Parcel> readFromStringByName(String input) {
        List<String> names = Arrays.stream(input.split(PARCEL_NAME_DELIMITER))
                .map(String::trim)
                .toList();
        return names.stream()
                .map(name -> parcelTypeService
                        .getByName(name)
                        .orElseThrow(
                                () -> new AppException("Parcel type with name '" + name + "' not found")
                        )
                )
                .map(Parcel::new)
                .toList();
    }

    private List<Parcel> readFromFileByName(String fileName) {
        try {
            String namesString = String.join(
                    PARCEL_NAME_DELIMITER,
                    Files.readAllLines(Path.of(fileName)).stream()
                            .filter(l -> !l.isEmpty())
                            .toList()
            );
            return readFromStringByName(namesString);
        } catch (IOException e) {
            throw new AppException("IOException occurred while reading parcel names file '" +
                    fileName + "': " + e.getMessage());
        }
    }

    private List<Parcel> readFromFileByForm(String fileName) {

        try (BufferedReader bufferedParcelReader = new BufferedReader(new FileReader(fileName))) {

            List<Parcel> parcels = new ArrayList<>();
            List<String> currentParcel = new ArrayList<>();

            String line;
            while ((line = bufferedParcelReader.readLine()) != null) {

                if (!line.isEmpty()) {
                    currentParcel.add(line);
                } else {
                    if (!currentParcel.isEmpty()) {

                        Parcel parcel = extractParcel(currentParcel);
                        log.debug("Parcel extracted: {}", parcel);
                        parcels.add(parcel);
                        currentParcel.clear();
                    }
                }
            }

            if (!currentParcel.isEmpty()) {

                Parcel parcel = extractParcel(currentParcel);
                log.debug("Parcel extracted: {}", parcel);
                parcels.add(parcel);
            }
            log.debug("Parcels read: {}", parcels);
            return parcels;


        } catch (IOException e) {
            throw new RuntimeException("IOException occurred while reading parcel file '"
                    + fileName + "': " + e.getMessage());
        }


    }

    private Parcel extractParcel(List<String> parcelLines) {
        ParcelDto parcelExtractedWithoutName = extractParcelWithoutName(parcelLines);
        ParcelType parcelType = parcelTypeService.getByShapeAndSymbol(
                parcelExtractedWithoutName.getShape(),
                parcelExtractedWithoutName.getSymbol()
        ).orElseThrow(() ->
                new AppException("Parcel type with given symbol '" + parcelExtractedWithoutName.getSymbol() +
                        "' and shape " +
                        Arrays.deepToString(parcelExtractedWithoutName.getShape())
                        + " not found")
        );
        return new Parcel(parcelType);
    }

    private ParcelDto extractParcelWithoutName(List<String> parcelLines) {

        int height = parcelLines.size();
        int maxWidth = parcelLines.stream()
                .map(String::length)
                .max(Integer::compareTo)
                .orElseThrow(
                        () -> new AppException("Invalid parcel format: " + parcelLines)
                );

        boolean[][] shape = new boolean[height][maxWidth];

        char symbol = ' ';

        for (int i = 0; i < height; i++) {
            StringBuilder parcelLine = new StringBuilder(parcelLines.get(i));
            while (parcelLine.length() < maxWidth) {
                parcelLine.append(" ");
            }
            for (int j = 0; j < maxWidth; j++) {
                shape[i][j] = parcelLine.charAt(j) != ' ';
                if (shape[i][j] && symbol == ' ') {
                    symbol = parcelLine.charAt(j);
                }
            }
        }

        return ParcelDto.builder()
                .shape(shape)
                .symbol(symbol)
                .build();

    }


}
