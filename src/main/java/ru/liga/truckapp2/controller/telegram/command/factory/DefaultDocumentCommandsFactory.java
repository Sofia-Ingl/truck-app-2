package ru.liga.truckapp2.controller.telegram.command.factory;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import ru.liga.truckapp2.controller.telegram.command.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class DefaultDocumentCommandsFactory implements DocumentCommandsFactory {

    private final Map<String, Command<Optional<SendDocument>>> commands;

    public DefaultDocumentCommandsFactory() {
        commands = new HashMap<>();
    }

    @Override
    public void register(String tgCommandString, Command<Optional<SendDocument>> command) {
        commands.put(tgCommandString, command);
    }

    @Override
    public Command<Optional<SendDocument>> getCommand(String tgCommandString) {
        return commands.get(tgCommandString);
    }
}
