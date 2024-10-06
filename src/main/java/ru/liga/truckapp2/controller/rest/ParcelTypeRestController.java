package ru.liga.truckapp2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.mapper.ParcelTypeMapper;
import ru.liga.truckapp2.service.ParcelTypeService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/truck-app/parcel-types")
public class ParcelTypeRestController {

    private final ParcelTypeService parcelTypeService;
    private final ParcelTypeMapper parcelTypeMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ParcelTypeDto> getAllParcelTypes() {
        return parcelTypeMapper.parcelTypesToDtoList(parcelTypeService.getAll());
    }


}
