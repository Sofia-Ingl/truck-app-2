package ru.liga.truckapp2.controller.telegram.command;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.truckapp2.controller.telegram.command.factory.TextCommandsFactory;
import ru.liga.truckapp2.dto.ParcelTypeCreateDto;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.service.ParcelTypeService;
import ru.liga.truckapp2.util.Stringifier;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParcelTypeCreateCommand implements Command<SendMessage> {

    private final TextCommandsFactory textCommandsFactory;
    private final ParcelTypeService parcelTypeService;
    private final Stringifier stringifier;
    private final Gson gson;

    @PostConstruct
    public void registerBean() {
        textCommandsFactory.register(getName(), this);
        log.info("Text command with name '{}' registered", getName());
    }

    @Override
    public String getName() {
        return "/create_parcel_type";
    }

    @Override
    public SendMessage apply(Update update, String documentPath) {

        String message = update.getMessage().getText().trim();
        String[] messageParts = message.split(" ", 2);

        if (messageParts.length != 2) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "Invalid usage: try '/create_parcel_type typeDescriptionInJson'"
            );
        }

        String typeDescriptionInJson = messageParts[1];
        ParcelTypeCreateDto parcelTypeCreateDto = gson.fromJson(typeDescriptionInJson, ParcelTypeCreateDto.class);

        ParcelType createdParcelType = parcelTypeService.create(parcelTypeCreateDto);

        return new SendMessage(
                update.getMessage().getChatId().toString(),
                "Parcel type created: \n" + stringifier.stringifyParcelType(createdParcelType)
        );
    }


}
