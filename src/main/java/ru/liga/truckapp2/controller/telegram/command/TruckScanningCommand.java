package ru.liga.truckapp2.controller.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.truckapp2.controller.telegram.command.factory.TextCommandsFactory;
import ru.liga.truckapp2.dto.CountedTruckDto;
import ru.liga.truckapp2.service.TruckService;
import ru.liga.truckapp2.util.Stringifier;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class TruckScanningCommand implements Command<SendMessage> {

    private final TextCommandsFactory textCommandsFactory;
    private final TruckService truckService;

    private final Stringifier stringifier;

    @PostConstruct
    public void registerBean() {
        textCommandsFactory.register(getName(), this);
        log.info("Text command with name '{}' registered", getName());
    }

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
