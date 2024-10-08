package ru.liga.truckapp2.controller.telegram.command;

import org.springframework.lang.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command<T> {

    /**
     * @return имя команды
     */
    String getName();

    /**
     * @param update       обновление в чате
     * @param documentPath путь к скачанному файлу (может быть null)
     * @return сущность для отправки пользователю
     */
    T apply(Update update, @Nullable String documentPath);

}
