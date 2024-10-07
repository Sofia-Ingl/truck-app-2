package ru.liga.truckapp2.controller.telegram.command.factory;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.truckapp2.controller.telegram.command.Command;

public interface TextCommandsFactory {

    void register(String tgCommandString, Command<SendMessage> command);

    Command<SendMessage> getCommand(String tgCommandString);
}
