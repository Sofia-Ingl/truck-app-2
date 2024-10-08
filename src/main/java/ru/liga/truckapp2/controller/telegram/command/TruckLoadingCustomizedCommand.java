package ru.liga.truckapp2.controller.telegram.command;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.truckapp2.dto.CustomizedLoadingTaskDto;
import ru.liga.truckapp2.service.TruckService;

import java.io.File;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class TruckLoadingCustomizedCommand implements Command<Optional<SendDocument>> {

    private final TruckService truckService;
    private final Gson gson;

    @Override
    public String getName() {
        return "/load_trucks_customized";
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

        log.debug("Message for load trucks customized command: {}", message);

        if (messageParts.length != 2) {
            return Optional.empty();
        }

        String jsonLoadingTask = messageParts[1].trim();

        CustomizedLoadingTaskDto task = gson.fromJson(jsonLoadingTask,
                CustomizedLoadingTaskDto.class
        );

        if (task.getTruckShapesFromFile()) {
            return Optional.empty();
        }

        truckService.loadParcelsWithTruckSizesCustomized(
                false,
                task.getTruckShapesIn(),
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
