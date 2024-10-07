package ru.liga.truckapp2.controller.telegram.command;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.truckapp2.controller.telegram.command.factory.TextCommandsFactory;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.service.ParcelTypeService;
import ru.liga.truckapp2.util.Stringifier;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParcelTypeUpdateCommand implements Command<SendMessage> {

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
        return "/update_parcel_type";
    }

    @Override
    public SendMessage apply(Update update, String documentPath) {

        String message = update.getMessage().getText().trim();

        int jsonStartIdx = message.indexOf('{');

        if (jsonStartIdx == -1) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "Invalid usage: try '/update_parcel_type typeName updatesDescriptionInJson'"
            );
        }

        String[] commandWithName = message.substring(0, jsonStartIdx).split(" ", 2);
        if (commandWithName.length != 2) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "Invalid usage: try '/update_parcel_type typeName updatesDescriptionInJson'"
            );
        }
        String name = commandWithName[1].trim();
        String updateJson = message.substring(jsonStartIdx);

        ParcelTypeDto parcelTypeCreateDto = gson.fromJson(updateJson, ParcelTypeDto.class);

        ParcelType updatedParcelType = parcelTypeService.update(name, parcelTypeCreateDto);

        return new SendMessage(
                update.getMessage().getChatId().toString(),
                "Parcel type updated: \n" + stringifier.stringifyParcelType(updatedParcelType)
        );
    }
}
