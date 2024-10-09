package ru.liga.truckapp2.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.CustomizedLoadingTaskDto;
import ru.liga.truckapp2.dto.DefaultLoadingTaskDto;
import ru.liga.truckapp2.util.Base64Decoder;

@Component
@RequiredArgsConstructor
public class RestDtoTransformationService {

    private final Base64Decoder base64Decoder;

    public CustomizedLoadingTaskDto transform(CustomizedLoadingTaskDto loadingTaskDto) {
        if (loadingTaskDto.getTruckShapesFromFile()) {
            loadingTaskDto.setTruckShapesIn(base64Decoder.decodeString(loadingTaskDto.getTruckShapesIn()));
            loadingTaskDto.setTruckShapesFromFile(false);
        }

        if (loadingTaskDto.getParcelsFromFile()) {
            loadingTaskDto.setParcelIn(base64Decoder.decodeString(loadingTaskDto.getParcelIn()));
            loadingTaskDto.setParcelsFromFile(false);
        }
        return loadingTaskDto;
    }


    public DefaultLoadingTaskDto transform(DefaultLoadingTaskDto loadingTaskDto) {
        if (loadingTaskDto.getParcelsFromFile()) {
            loadingTaskDto.setParcelIn(base64Decoder.decodeString(loadingTaskDto.getParcelIn()));
            loadingTaskDto.setParcelsFromFile(false);
        }
        return loadingTaskDto;
    }


}
