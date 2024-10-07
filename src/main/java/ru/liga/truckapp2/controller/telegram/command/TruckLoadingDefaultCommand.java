package ru.liga.truckapp2.controller.telegram.command;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.truckapp2.controller.telegram.command.factory.DocumentCommandsFactory;
import ru.liga.truckapp2.dto.DefaultLoadingTaskDto;
import ru.liga.truckapp2.service.TruckService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class TruckLoadingDefaultCommand implements Command<Optional<SendDocument>> {

    private final DocumentCommandsFactory documentCommandsFactory;
    private final TruckService truckService;
    private final Gson gson;

    @PostConstruct
    public void registerBean() {
        documentCommandsFactory.register(getName(), this);
        log.info("Document command with name '{}' registered", getName());
    }

    @Override
    public String getName() {
        return "/load_trucks";
    }

    @Override
    public Optional<SendDocument> apply(Update update,
                              String documentPath
    ) {

        String message;
        if (update.getMessage().hasText()) {
            message = update.getMessage().getText().trim();
        } else {
            message = update.getMessage().getCaption().trim();
        }
        String[] messageParts = message.split(" ", 2);

        if (messageParts.length != 2) {
            return Optional.empty();
        }

        String jsonLoadingTask = messageParts[1].trim();

        DefaultLoadingTaskDto task = gson.fromJson(jsonLoadingTask, DefaultLoadingTaskDto.class);

        truckService.loadParcels(
                task.getWidth(),
                task.getHeight(),
                task.getQuantity(),
                task.getParcelsFromFile(),
                task.getParcelsByForm(),
                (task.getParcelsFromFile()) ? documentPath : task.getParcelIn(),
                task.getAlgorithm(),
                task.getOut()
        );
        File file = new File(task.getOut());
        return Optional.of(SendDocument.builder()
                .chatId(update.getMessage().getChatId())
                .caption("File with loaded trucks")
                .document(new InputFile(file))
                .build());
    }
}
