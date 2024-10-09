package ru.liga.truckapp2.controller.rest.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.liga.truckapp2.exception.AppException;

@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<String> handleAppException(final AppException appException) {
        log.error(appException.getMessage());
        String stringBuilder = "Exception: " + "\n" +
                appException.getMessage();
        return ResponseEntity.badRequest().body(stringBuilder);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleAppException(final RuntimeException runtimeException) {
        log.error(runtimeException.getMessage());
        String stringBuilder = "Exception (" + runtimeException.getClass() + "):" + "\n" +
                runtimeException.getMessage();
        return ResponseEntity.badRequest().body(stringBuilder);
    }

}
