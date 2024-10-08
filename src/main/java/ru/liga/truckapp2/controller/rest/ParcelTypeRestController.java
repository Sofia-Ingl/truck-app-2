package ru.liga.truckapp2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.truckapp2.dto.ParcelTypeCreateDto;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.mapper.ParcelTypeMapper;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.service.ParcelTypeService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/truck-app/parcel-types")
public class ParcelTypeRestController {

    private final ParcelTypeService parcelTypeService;
    private final ParcelTypeMapper parcelTypeMapper;

    @GetMapping
    public ResponseEntity<List<ParcelTypeDto>> getAllParcelTypes() {
        return ResponseEntity.ok().body(
                parcelTypeMapper.parcelTypesToDtoList(parcelTypeService.getAll())
        );
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getParcelType(@PathVariable("name") String name) {
        ParcelType parcelType = parcelTypeService.getByName(name).orElse(null);
        return (parcelType != null) ?
                ResponseEntity.ok().body(
                        parcelTypeMapper.parcelTypeToDto(parcelType)
                ) :
                ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<ParcelTypeDto> createParcelType(
            @RequestBody @Valid ParcelTypeCreateDto createDto
    ) {
        ParcelType parcelType = parcelTypeService.create(createDto);
        return ResponseEntity.ok().body(
                parcelTypeMapper.parcelTypeToDto(parcelType)
        );
    }

    @PatchMapping("/{name}")
    public ResponseEntity<ParcelTypeDto> updateParcelType(
            @PathVariable("name") String name,
            @RequestBody @Valid ParcelTypeDto updateDto
    ) {
        ParcelType parcelType = parcelTypeService.update(name, updateDto);
        return ResponseEntity.ok().body(
                parcelTypeMapper.parcelTypeToDto(parcelType)
        );
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Boolean> deleteParcelType(
            @PathVariable("name") String name
    ) {
        boolean deleted = parcelTypeService.delete(name);
        return ResponseEntity.ok().body(deleted);
    }

}
