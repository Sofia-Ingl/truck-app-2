package ru.liga.truckapp2.service;

public interface TempFilesService {

    String createTempFileFromContent(String base64Content);

    void deleteFile(String fileName);
}
