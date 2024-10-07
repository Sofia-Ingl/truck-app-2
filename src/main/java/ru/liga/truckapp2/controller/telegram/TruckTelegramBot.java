package ru.liga.truckapp2.controller.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.truckapp2.controller.telegram.command.Command;
import ru.liga.truckapp2.controller.telegram.command.factory.DocumentCommandsFactory;
import ru.liga.truckapp2.controller.telegram.command.factory.TextCommandsFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class TruckTelegramBot extends TelegramLongPollingBot {

    @Value("${bot.token}")
    private String botToken;
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.files.dir}")
    private String filesDirectory;

    private final String INVALID_COMMAND_MESSAGE = "Invalid command";

    private final TelegramBotsApi telegramBotsApi;
    private final TextCommandsFactory textCommandsFactory;
    private final DocumentCommandsFactory documentCommandsFactory;

    @PostConstruct
    public void init() {
        try {
            telegramBotsApi.registerBot(this);
            log.info("Telegram bot is ready");
        } catch (TelegramApiException e) {
            log.error("Tg bot cannot be registered: {}", e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        String chatId = update.getMessage().getChatId().toString();

        try {

            if (update.hasMessage() && (update.getMessage().hasText() || update.getMessage().getCaption() != null)) {

                String commandName;
                String documentPath;
                if (update.getMessage().hasText()) {
                    commandName = update.getMessage().getText().trim().split(" ")[0];
                    documentPath = null;
                } else {
                    commandName = update.getMessage().getCaption().trim().split(" ")[0];
                    Files.createDirectories(Path.of(filesDirectory));
                    documentPath = filesDirectory + update.getMessage().getChatId().toString();
                    String fileId = update.getMessage().getDocument().getFileId();
                    downLoadFile(documentPath, fileId);
                }

                Command<SendMessage> textCommand = textCommandsFactory.getCommand(commandName);
                Command<Optional<SendDocument>> documentCommand = documentCommandsFactory.getCommand(commandName);

                if (textCommand != null) {
                    SendMessage sendMessage = textCommand.apply(update, documentPath);
                    sendMessage(sendMessage);
                    log.debug("Text command with name '{}' applied", commandName);
                }

                if (documentCommand != null) {
                    SendDocument sendDocument = documentCommand.apply(update, documentPath).orElse(null);
                    if (sendDocument == null) {
                        sendMessage(new SendMessage(chatId,
                                "Unable to process document command '" + commandName + "': please check input data")
                        );
                    } else {
                        sendDocument(sendDocument);
                        log.debug("Document command with name '{}' applied", commandName);
                    }

                }

                if (documentCommand == null && textCommand == null) {
                    sendMessage(new SendMessage(chatId, INVALID_COMMAND_MESSAGE));
                    log.debug("No command found for query {}", update.getMessage().getText());
                }

            } else {
                sendMessage(new SendMessage(chatId, INVALID_COMMAND_MESSAGE));
                log.debug("No command found for query {}", update.getMessage().getText());
            }

        } catch (Exception e) {
            log.error("Error occurred: {}", e.getMessage());
            sendMessage(new SendMessage(chatId, e.getMessage()));
        }

    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }


    private void sendDocument(SendDocument sendDocument) {
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void downLoadFile(String pathToLoad, String fileId) {

        GetFile getFile = new GetFile();
        getFile.setFileId(fileId);
        try {
            org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
            downloadFile(file, new File(pathToLoad));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }

    }
}
