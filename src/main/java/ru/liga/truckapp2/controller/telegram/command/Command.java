package ru.liga.truckapp2.controller.telegram.command;

import org.springframework.lang.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command<T> {

    String getName();

    T apply(Update update, @Nullable String documentPath);

}
