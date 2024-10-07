package ru.liga.truckapp2.controller.telegram.command.factory;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import ru.liga.truckapp2.controller.telegram.command.Command;

import java.util.Optional;

public interface DocumentCommandsFactory {

    Command<Optional<SendDocument>> getCommand(String tgCommandString);

}
