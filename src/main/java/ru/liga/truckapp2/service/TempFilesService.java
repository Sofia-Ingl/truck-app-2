package ru.liga.truckapp2.service;

public interface TempFilesService {

    /**
     * @param content содержимое (текст) для записи во временный файл
     * @return имя файла
     */
    String createTempFileFromContent(String content);

    /**
     * @param fileName имя файла
     */
    void deleteFile(String fileName);
}
