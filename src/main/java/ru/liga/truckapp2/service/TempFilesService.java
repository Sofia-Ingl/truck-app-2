package ru.liga.truckapp2.service;

public interface TempFilesService {

    String createTempFileFromContent(String content);

    void deleteFile(String fileName);
}
