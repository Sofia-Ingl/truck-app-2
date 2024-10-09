package ru.liga.truckapp2.controller.telegram.command.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import ru.liga.truckapp2.controller.telegram.command.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class DefaultDocumentCommandsFactory implements DocumentCommandsFactory {

    private final Map<String, Command<Optional<SendDocument>>> commands;

    public DefaultDocumentCommandsFactory(List<Command<Optional<SendDocument>>> commandsList) {
        commands = new HashMap<>();
        for (Command<Optional<SendDocument>> command : commandsList) {
            commands.put(command.getName(), command);
            log.info("Added document command: {}", command.getName());
        }
    }

    @Override
    public Command<Optional<SendDocument>> getCommand(String tgCommandString) {
        return commands.get(tgCommandString);
    }
}
