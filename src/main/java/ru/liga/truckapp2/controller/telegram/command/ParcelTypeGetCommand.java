package ru.liga.truckapp2.controller.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.service.ParcelTypeService;
import ru.liga.truckapp2.util.Stringifier;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParcelTypeGetCommand implements Command<SendMessage> {

    private final ParcelTypeService parcelTypeService;
    private final Stringifier stringifier;

    @Override
    public String getName() {
        return "/get_parcel_type";
    }

    @Override
    public SendMessage apply(String textArguments, String documentPath, Long chatId) {
        if (textArguments == null || textArguments.isEmpty()) {
            return new SendMessage(chatId.toString(),
                    "Invalid usage: try '/delete_parcel_type typeName'");
        }
        String name = textArguments.trim();
        Optional<ParcelType> parcelType = parcelTypeService.getByName(name);
        if (parcelType.isEmpty()) {
            return new SendMessage(chatId.toString(),
                    "Parcel type '" + name + "' not found");
        }

        return new SendMessage(chatId.toString(),
                stringifier.stringifyParcelType(parcelType.orElse(null)));
    }
}
