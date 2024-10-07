package ru.liga.truckapp2.controller.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.truckapp2.controller.telegram.command.factory.TextCommandsFactory;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.service.ParcelTypeService;
import ru.liga.truckapp2.util.Stringifier;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParcelTypeGetCommand implements Command<SendMessage> {

    private final TextCommandsFactory textCommandsFactory;
    private final ParcelTypeService parcelTypeService;
    private final Stringifier stringifier;

    @PostConstruct
    public void registerBean() {
        textCommandsFactory.register(getName(), this);
        log.info("Text command with name '{}' registered", getName());
    }

    @Override
    public String getName() {
        return "/get_parcel_type";
    }

    @Override
    public SendMessage apply(Update update, String documentPath) {

        String message = update.getMessage().getText().trim();
        String[] messageParts = message.split(" ", 2);

        if (messageParts.length != 2) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "Invalid usage: try '/get_parcel_type typeName'"
            );
        }

        String name = messageParts[1].trim();
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
