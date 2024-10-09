package ru.liga.truckapp2.controller.telegram.command.factory;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import ru.liga.truckapp2.controller.telegram.command.Command;

import java.util.Optional;

public interface DocumentCommandsFactory {

    /**
     * @param tgCommandString название команды, отправленное пользователем в тг
     * @return команда, возвращающая документ, или null, если такой документной команды нет
     */
    Command<Optional<SendDocument>> getCommand(String tgCommandString);

}
