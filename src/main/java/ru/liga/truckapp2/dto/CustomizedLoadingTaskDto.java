package ru.liga.truckapp2.dto;

import lombok.Getter;
import ru.liga.truckapp2.model.PackagingAlgorithmType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CustomizedLoadingTaskDto {

    @NotNull
    private PackagingAlgorithmType algorithm;
    @NotNull
    Boolean truckShapesFromFile;
    @NotNull
    private Boolean parcelsFromFile;
    @NotNull
    private Boolean parcelsByForm;
    @NotNull
    String truckShapesIn;
    @NotBlank
    private String parcelIn;
    @NotBlank
    String out;

}
