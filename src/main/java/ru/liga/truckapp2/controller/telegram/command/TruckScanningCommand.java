package ru.liga.truckapp2.controller.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.service.TruckService;
import ru.liga.truckapp2.util.Stringifier;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class TruckScanningCommand implements Command<SendMessage> {

    private final TruckService truckService;

    private final Stringifier stringifier;

    @Override
    public String getName() {
        return "/scan_trucks";
    }

    @Override
    public SendMessage apply(Update update, String documentPath) {

        List<CountedTruckDto> countedTruckDtoList =
                truckService.countParcelsInTrucks(documentPath);
        return new SendMessage(update.getMessage().getChatId().toString(),
                stringifier.stringifyCountedTrucks(countedTruckDtoList));
    }
}
