package ru.liga.truckapp2.controller.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.truckapp2.service.ParcelTypeService;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParcelTypeDeleteCommand implements Command<SendMessage> {

    private final ParcelTypeService parcelTypeService;

    @Override
    public String getName() {
        return "/delete_parcel_type";
    }

    @Override
    public SendMessage apply(String textArguments, String documentPath, Long chatId) {

        if (textArguments == null || textArguments.isEmpty()) {
            return new SendMessage(chatId.toString(),
                    "Invalid usage: try '/delete_parcel_type typeName'");
        }
        String name = textArguments.trim();
        boolean deleted = parcelTypeService.delete(name);
        if (deleted) {
            return new SendMessage(chatId.toString(),
                    "Parcel type '" + name + "' deleted");
        }
        return new SendMessage(chatId.toString(),
                "Parcel type '" + name + "' not found");
    }
}
