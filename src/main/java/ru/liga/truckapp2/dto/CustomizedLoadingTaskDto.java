package ru.liga.truckapp2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.liga.truckapp2.model.PackagingAlgorithmType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
public class CustomizedLoadingTaskDto {

    @NotNull
    private PackagingAlgorithmType algorithm;
    @NotNull
    private Boolean truckShapesFromFile;
    @NotNull
    private Boolean parcelsFromFile;
    @NotNull
    private Boolean parcelsByForm;
    @NotNull
    private String truckShapesIn;
    @NotBlank
    private String parcelIn;
    @NotBlank
    private String out;

}
