package ru.liga.truckapp2.controller.telegram.command;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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

    @Override
    public String getName() {
        return "/create_parcel_type";
    }

    @Override
    public SendMessage apply(String textArguments, String documentPath, Long chatId) {

        if (textArguments == null || textArguments.isEmpty()) {
            return new SendMessage(chatId.toString(),
                    "Invalid usage: try '/create_parcel_type typeDescriptionInJson'"
            );
        }
        ParcelTypeCreateDto parcelTypeCreateDto = gson.fromJson(textArguments, ParcelTypeCreateDto.class);

        ParcelType createdParcelType = parcelTypeService.create(parcelTypeCreateDto);

        return new SendMessage(
                chatId.toString(),
                "Parcel type created: \n" + stringifier.stringifyParcelType(createdParcelType)
        );
    }


}
