package ru.liga.truckapp2.controller.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.truckapp2.dto.CustomizedLoadingTaskDto;
import ru.liga.truckapp2.dto.DefaultLoadingTaskDto;
import ru.liga.truckapp2.dto.LoadedTruckDto;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.service.TruckService;
import ru.liga.truckapp2.util.Stringifier;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@ShellComponent
@ShellCommandGroup("Truck loading commands")
public class TruckLoadingShellController {

    private final TruckService truckService;
    private final Stringifier stringifier;

    @ShellMethod(key = "load-trucks",
            value = "Возвращает список загруженных грузовиков")
    public String loadTrucks(
            @ShellOption(defaultValue = "6", help = "ширина грузовика") Integer width,
            @ShellOption(defaultValue = "6", help = "высота грузовика") Integer height,
            @ShellOption(defaultValue = "10", help = "количество грузовиков") Integer quantity,
            @ShellOption(help = "алгоритм погрузки") PackagingAlgorithmType algorithm,
            @ShellOption(help = "посылки берутся из файла или нет") Boolean parcelsFromFile,
            @ShellOption(help = "посылки считываются по форме или нет") Boolean parcelsByForm,
            @ShellOption(help = "вход посылок (имя файла или строка с именами, трактуется в зависимости от арумента parcelsFromFile)") String parcelIn,
            @ShellOption(help = "файл для записи результата") String out) {


        List<LoadedTruckDto> loadedTrucks = truckService.loadParcels(
                DefaultLoadingTaskDto
                        .builder()
                        .width(width)
                        .height(height)
                        .quantity(quantity)
                        .parcelsFromFile(parcelsFromFile)
                        .parcelsByForm(parcelsByForm)
                        .parcelIn(parcelIn)
                        .algorithm(algorithm)
                        .out(out)
                        .build());

        return stringifier.stringifyLoadedTrucks(loadedTrucks);
    }

    @ShellMethod(key = "load-trucks-customized",
            value = "Возвращает список загруженных грузовиков")
    public String loadTrucksCustomized(
            @ShellOption(help = "алгоритм погрузки") PackagingAlgorithmType algorithm,
            @ShellOption(help = "формы грузовиков лежат в файле или нет") Boolean truckShapesFromFile,
            @ShellOption(help = "посылки берутся из файла или нет") Boolean parcelsFromFile,
            @ShellOption(help = "посылки считываются по форме или нет") Boolean parcelsByForm,
            @ShellOption(help = "вход посылок (имя файла или строка с именами)") String parcelIn,
            @ShellOption(help = "строка с формами грузовиков или имя файла, где она лежит") String truckShapesIn,
            @ShellOption(help = "файл для записи результата") String out) {

        List<LoadedTruckDto> loadedTrucks = truckService.loadParcels(
                CustomizedLoadingTaskDto
                        .builder()
                        .truckShapesFromFile(truckShapesFromFile)
                        .truckShapesIn(truckShapesIn)
                        .parcelsFromFile(parcelsFromFile)
                        .parcelsByForm(parcelsByForm)
                        .parcelIn(parcelIn)
                        .algorithm(algorithm)
                        .out(out)
                        .build());

        return stringifier.stringifyLoadedTrucks(loadedTrucks);
    }

}
