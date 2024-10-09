package ru.liga.truckapp2.controller.telegram.command;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.truckapp2.dto.ParcelTypeCreateDto;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.service.ParcelTypeService;
import ru.liga.truckapp2.util.Stringifier;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParcelTypeCreateCommand implements Command<SendMessage> {

    private final ParcelTypeService parcelTypeService;
    private final Stringifier stringifier;
    private final Gson gson;

    private static final String TG_MESSAGE_PARTS_DELIMITER = " ";
    private static final Integer TG_MESSAGE_PARTS_NUMBER = 2;
    private static final Integer COMMAND_ARG_INDEX = 1;

    @Override
    public String getName() {
        return "/create_parcel_type";
    }

    @Override
    public SendMessage apply(Update update, String documentPath) {

        String message = update.getMessage().getText().trim();
        String[] messageParts = message.split(TG_MESSAGE_PARTS_DELIMITER, TG_MESSAGE_PARTS_NUMBER);

        log.debug("Message for create parcel type command: {}", message);

        if (messageParts.length != TG_MESSAGE_PARTS_NUMBER) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "Invalid usage: try '/create_parcel_type typeDescriptionInJson'"
            );
        }

        String typeDescriptionInJson = messageParts[COMMAND_ARG_INDEX];
        ParcelTypeCreateDto parcelTypeCreateDto = gson.fromJson(typeDescriptionInJson, ParcelTypeCreateDto.class);

        ParcelType createdParcelType = parcelTypeService.create(parcelTypeCreateDto);

        return new SendMessage(
                update.getMessage().getChatId().toString(),
                "Parcel type created: \n" + stringifier.stringifyParcelType(createdParcelType)
        );
    }


}
