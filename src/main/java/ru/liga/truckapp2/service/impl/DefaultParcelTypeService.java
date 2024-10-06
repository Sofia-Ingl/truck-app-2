package ru.liga.truckapp2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.mapper.ParcelTypeMapper;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.repository.ParcelTypeJpaRepository;
import ru.liga.truckapp2.service.ParcelTypeService;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultParcelTypeService implements ParcelTypeService {

    private final ParcelTypeJpaRepository parcelTypeJpaRepository;
    private final ParcelTypeMapper parcelTypeMapper;

    @Override
    public List<ParcelType> getAll() {
        return parcelTypeJpaRepository.findAll();
    }

    @Override
    public Optional<ParcelType> getByName(String name) {
        return parcelTypeJpaRepository.findByName(name.trim());
    }

    @Override
    public Optional<ParcelType> getByShapeAndSymbol(boolean[][] shape, char symbol) {
        return parcelTypeJpaRepository.findByShapeAndSymbol(shape, symbol);
    }

    @Override
    public ParcelType create(ParcelTypeDto createDto) {
        ParcelType parcelType = parcelTypeMapper.dtoToParcelType(createDto);
        try {
            return parcelTypeJpaRepository.save(parcelType);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(
                    "Could not save parcel type " + parcelType + ":\n"
                            + e.getClass() + ": " + e.getMessage());
        }
    }

    @Override
    public ParcelType update(String name,
                             ParcelTypeDto updateDto) {

        ParcelType parcelType = getByName(name).orElseThrow(
                () -> new AppException("Could not find parcel type with name " + name)
        );
        Integer id = parcelType.getId();
        String newName = (updateDto.getName() != null) ? updateDto.getName() : parcelType.getName();
        boolean[][] newShape = (updateDto.getShape() != null) ?
                updateDto.getShape() : parcelType.getShape();
        char newSymbol = (updateDto.getSymbol() != null) ? updateDto.getSymbol() : parcelType.getSymbol();
        ParcelType updatedParcelType = new ParcelType(id, newName, newShape, newSymbol);

        try {
            return parcelTypeJpaRepository.save(updatedParcelType);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(
                    "Could not update parcel type named '" + name
                            + "' with name " + newName + " and shape '"
                            + Arrays.deepToString(newShape) + "' and symbol " + newSymbol
                            + ":\n" + e.getClass()
                            + ": " + e.getMessage()
            );
        }
    }

    @Override
    @Transactional
    public boolean delete(String name) {
        return parcelTypeJpaRepository.deleteByName(name.trim()) == 1;
    }

}
