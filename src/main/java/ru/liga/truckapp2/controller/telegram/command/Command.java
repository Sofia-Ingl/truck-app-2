package ru.liga.truckapp2.controller.telegram.command;

import org.springframework.lang.Nullable;

public interface Command<T> {

    /**
     * @return имя команды
     */
    String getName();

    /**
     * @param textArguments текстовые аргументы
     * @param documentPath  путь к скачанному файлу (может быть null)
     * @param chatId        id чата
     * @return сущность для отправки пользователю
     */
    T apply(String textArguments, @Nullable String documentPath, Long chatId);

}
