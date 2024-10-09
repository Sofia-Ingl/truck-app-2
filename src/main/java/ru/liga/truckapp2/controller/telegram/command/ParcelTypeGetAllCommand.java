package ru.liga.truckapp2.controller.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.service.ParcelTypeService;
import ru.liga.truckapp2.util.Stringifier;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParcelTypeGetAllCommand implements Command<SendMessage> {

    private final ParcelTypeService parcelTypeService;
    private final Stringifier stringifier;

    @Override
    public String getName() {
        return "/all_parcel_types";
    }

    @Override
    public SendMessage apply(String textArguments, String documentPath, Long chatId) {
        List<ParcelType> parcelTypes = parcelTypeService.getAll();
        return new SendMessage(chatId.toString(),
                stringifier.stringifyParcelTypesList(parcelTypes));
    }

}
