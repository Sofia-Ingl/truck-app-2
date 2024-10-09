package ru.liga.truckapp2.controller.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
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

    private static final String TG_MESSAGE_PARTS_DELIMITER = " ";
    private static final Integer TG_MESSAGE_PARTS_NUMBER = 2;
    private static final Integer COMMAND_ARG_INDEX = 1;

    @Override
    public String getName() {
        return "/get_parcel_type";
    }

    @Override
    public SendMessage apply(Update update, String documentPath) {

        String message = update.getMessage().getText().trim();
        String[] messageParts = message.split(TG_MESSAGE_PARTS_DELIMITER, TG_MESSAGE_PARTS_NUMBER);

        log.debug("Message for get parcel type command: {}", message);

        if (messageParts.length != TG_MESSAGE_PARTS_NUMBER) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "Invalid usage: try '/get_parcel_type typeName'"
            );
        }

        String name = messageParts[COMMAND_ARG_INDEX].trim();
        Optional<ParcelType> parcelType = parcelTypeService.getByName(name);

        if (parcelType.isEmpty()) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "Parcel type '" + name + "' not found"
            );
        }

        return new SendMessage(
                update.getMessage().getChatId().toString(),
                stringifier.stringifyParcelType(parcelType.orElse(null))
        );
    }
}
