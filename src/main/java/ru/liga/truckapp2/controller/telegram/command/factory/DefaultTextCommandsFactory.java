package ru.liga.truckapp2.controller.telegram.command.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.truckapp2.controller.telegram.command.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DefaultTextCommandsFactory implements TextCommandsFactory {

    private final Map<String, Command<SendMessage>> commands;

    public DefaultTextCommandsFactory(List<Command<SendMessage>> commandsList) {
        commands = new HashMap<>();
        for (Command<SendMessage> command : commandsList) {
            commands.put(command.getName(), command);
            log.info("Added text command: {}", command.getName());
        }
    }

    @Override
    public Command<SendMessage> getCommand(String tgCommandString) {
        return commands.get(tgCommandString);
    }
}
