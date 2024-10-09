package ru.liga.truckapp2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.liga.truckapp2.model.PackagingAlgorithmType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
@Builder
public class DefaultLoadingTaskDto {

    @NotNull
    @Positive
    private Integer width;
    @NotNull
    @Positive
    private Integer height;
    @NotNull
    @Positive
    private Integer quantity;
    @NotNull
    private PackagingAlgorithmType algorithm;
    @NotNull
    private Boolean parcelsFromFile;
    @NotNull
    private Boolean parcelsByForm;
    @NotBlank
    private String parcelIn;
    @NotBlank
    private String out;

}
