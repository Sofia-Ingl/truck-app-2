package ru.liga.truckapp2.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.repository.ParcelTypeRepository;
import ru.liga.truckapp2.service.ParcelTypeService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultParcelTypeService implements ParcelTypeService {

    private final ParcelTypeRepository parcelTypeRepository;

    public List<ParcelType> getAll() {
        return parcelTypeRepository.findAll();
    }

    public Optional<ParcelType> getByName(String name) {
        return parcelTypeRepository.findByName(name.trim());
    }

    @Override
    public Optional<ParcelType> getByShapeAndSymbol(boolean[][] shape, char symbol) {
        return parcelTypeRepository.findByShapeAndSymbol(shape, symbol);
    }

    public ParcelType create(ParcelTypeDto createDto) {
        return parcelTypeRepository.save(createDto);
    }

    public ParcelType update(String name,
                             ParcelTypeDto updateDto) {
        return parcelTypeRepository.updateByName(name, updateDto);
    }

    public boolean delete(String name) {
        return parcelTypeRepository.deleteByName(name.trim());
    }

}
