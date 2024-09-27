package ru.liga.truckapp2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.ParcelCreateDto;
import ru.liga.truckapp2.dto.ParcelUpdateDto;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.repository.ParcelRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ParcelService {

    private final ParcelRepository parcelRepository;


    public List<Parcel> getAll() {
        return parcelRepository.findAll();
    }

    public Optional<Parcel> getByName(String name) {
        return parcelRepository.findByName(name.trim());
    }

    public Parcel createParcel(String name,
                               String shape,
                               Character symbol) {
        ParcelCreateDto createDto = new ParcelCreateDto(
                name.trim(),
                getShapeFromString(shape),
                symbol
        );
        return parcelRepository.save(createDto);
    }

    public Parcel updateParcel(String name,
                               String newName,
                               String newShape,
                               Character newSymbol) {
        ParcelUpdateDto updateDto = ParcelUpdateDto.builder()
                .name(Objects.equals(newName, "") ? null: newName.trim())
                .shape(Objects.equals(newShape, "") ? null: getShapeFromString(newShape))
                .symbol(Objects.equals(newSymbol, ' ') ? null: newSymbol)
                .build();
        return parcelRepository.updateByName(name, updateDto);
    }

    public boolean deleteParcel(String name) {
        return parcelRepository.deleteByName(name.trim());
    }


    private boolean[][] getShapeFromString(String shape) {
        String[] lines = shape.split(",");
        if (lines.length == 0) {
            throw new AppException("Invalid shape: " + shape);
        }
        int height = lines.length;
        int width = lines[0].length();
        boolean[][] shapeArr = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            if (lines[i].length() != width) {
                throw new AppException("Invalid shape: " + shape + "; line of length " + width + " expected");
            }
            for (int j = 0; j < lines[i].length(); j++) {
                shapeArr[i][j] = lines[i].charAt(j) != ' ';
            }
        }
        return shapeArr;
    }
}
