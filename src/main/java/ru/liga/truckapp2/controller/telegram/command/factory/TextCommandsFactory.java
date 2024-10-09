package ru.liga.truckapp2.controller.telegram.command.factory;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.truckapp2.controller.telegram.command.Command;

public interface TextCommandsFactory {

    /**
     * @param tgCommandString название команды, отправленное пользователем в тг
     * @return команда, возвращающая текстовое сообщение, или null, если такой текстовой команды нет
     */
    Command<SendMessage> getCommand(String tgCommandString);
}
