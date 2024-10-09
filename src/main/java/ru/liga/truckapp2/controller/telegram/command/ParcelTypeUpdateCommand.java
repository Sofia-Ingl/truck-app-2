package ru.liga.truckapp2.controller.telegram.command;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.service.ParcelTypeService;
import ru.liga.truckapp2.util.Stringifier;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParcelTypeUpdateCommand implements Command<SendMessage> {

    private final ParcelTypeService parcelTypeService;
    private final Stringifier stringifier;
    private final Gson gson;

    @Override
    public String getName() {
        return "/update_parcel_type";
    }

    @Override
    public SendMessage apply(String textArguments, String documentPath, Long chatId) {

        if (!textArguments.contains("{")) {
            return new SendMessage(chatId.toString(),
                    "Invalid usage: try '/update_parcel_type typeName updatesDescriptionInJson'");
        }
        int jsonIdx = textArguments.indexOf("{");
        String name = textArguments.substring(0, jsonIdx).trim();
        String updateJson = textArguments.substring(jsonIdx);

        ParcelTypeDto parcelTypeUpdateDto = gson.fromJson(updateJson, ParcelTypeDto.class);
        ParcelType updatedParcelType = parcelTypeService.update(name, parcelTypeUpdateDto);

        return new SendMessage(chatId.toString(),
                "Parcel type updated: \n" + stringifier.stringifyParcelType(updatedParcelType));
    }
}
