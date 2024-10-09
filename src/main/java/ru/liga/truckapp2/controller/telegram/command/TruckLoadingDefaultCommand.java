package ru.liga.truckapp2.controller.telegram.command;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.liga.truckapp2.dto.DefaultLoadingTaskDto;
import ru.liga.truckapp2.service.TruckService;

import java.io.File;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class TruckLoadingDefaultCommand implements Command<Optional<SendDocument>> {

    private final TruckService truckService;
    private final Gson gson;

    @Override
    public String getName() {
        return "/load_trucks";
    }


    @Override
    public Optional<SendDocument> apply(String textArguments, String documentPath, Long chatId) {
        if (textArguments == null || textArguments.trim().isEmpty()) {
            return Optional.empty();
        }
        String jsonLoadingTask = textArguments.trim();
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
                .chatId(chatId)
                .caption("File with loaded trucks")
                .document(new InputFile(file))
                .build());

    }
}
