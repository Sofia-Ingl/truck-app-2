package ru.liga.truckapp2.service;

import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.model.PackagingAlgorithmType;

import java.util.List;

public interface TruckService {


    /**
     * @param file файл, откуда берутся загруженные грузовики
     * @return грузовики с подчитанными посылками
     */
    List<CountedTruckDto> countParcelsInTrucks(String file);

    /**
     * @param width           ширина грузовика
     * @param height          высота грузовика
     * @param quantity        количество грузовиков
     * @param parcelsFromFile посылки берутся из файла или нет
     * @param parcelsByForm   посылки считываются по форме или нет
     * @param parcelIn        вход посылок (имя файла или строка с именами, трактуется в зависимости от арумента parcelsFromFile)
     * @param algorithm       алгоритм погрузки
     * @param out             файл для записи результата
     * @return список загруженных грузовиков
     */
    List<LoadedTruckDto> loadParcels(Integer width,
                                     Integer height,
                                     Integer quantity,
                                     Boolean parcelsFromFile,
                                     Boolean parcelsByForm,
                                     String parcelIn,
                                     PackagingAlgorithmType algorithm,
                                     String out);

    /**
     * @param truckShapesFromFile формы грузовиков лежат в файле или нет
     * @param truckShapesIn       строка с формами грузовиков или имя файла, где она лежит
     * @param parcelsFromFile     посылки берутся из файла или нет
     * @param parcelsByForm       посылки считываются по форме или нет
     * @param parcelIn            вход посылок (имя файла или строка с именами, трактуется в зависимости от арумента parcelsFromFile)
     * @param algorithm           алгоритм погрузки
     * @param out                 файл для записи результата
     * @return список загруженных грузовиков
     */
    List<LoadedTruckDto> loadParcelsWithTruckSizesCustomized(Boolean truckShapesFromFile,
                                                             String truckShapesIn,
                                                             Boolean parcelsFromFile,
                                                             Boolean parcelsByForm,
                                                             String parcelIn,
                                                             PackagingAlgorithmType algorithm,
                                                             String out);
}
