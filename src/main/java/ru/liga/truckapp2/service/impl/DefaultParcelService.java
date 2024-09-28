package ru.liga.truckapp2.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.truckapp2.dto.ParcelDto;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.repository.ParcelRepository;
import ru.liga.truckapp2.service.ParcelService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultParcelService implements ParcelService {

    private final ParcelRepository parcelRepository;

    public List<Parcel> getAll() {
        return parcelRepository.findAll();
    }

    public Optional<Parcel> getByName(String name) {
        return parcelRepository.findByName(name.trim());
    }

    public Parcel createParcel(ParcelDto createDto) {
        return parcelRepository.save(createDto);
    }

    public Parcel updateParcel(String name,
                               ParcelDto updateDto) {
        return parcelRepository.updateByName(name, updateDto);
    }

    public boolean deleteParcel(String name) {
        return parcelRepository.deleteByName(name.trim());
    }

}
