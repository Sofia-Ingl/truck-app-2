package ru.liga.truckapp2.controller.telegram.command.factory;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.truckapp2.controller.telegram.command.Command;

import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultTextCommandsFactory implements TextCommandsFactory {

    private final Map<String, Command<SendMessage>> commands;

    public DefaultTextCommandsFactory() {
        commands = new HashMap<>();
    }

    @Override
    public void register(String tgCommandString, Command<SendMessage> command) {
        commands.put(tgCommandString, command);
    }

    @Override
    public Command<SendMessage> getCommand(String tgCommandString) {
        return commands.get(tgCommandString);
    }
}
