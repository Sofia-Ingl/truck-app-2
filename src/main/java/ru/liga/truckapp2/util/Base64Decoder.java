package ru.liga.truckapp2.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Slf4j
@Component
public class Base64Decoder {

    public String decodeString(String base64Content) {
        String content = new String(decode(base64Content));
        log.info("Decoded content: {}", content);
        return content;
    }

    private byte[] decode(String base64Content) {
        return Base64.getDecoder().decode(base64Content);
    }
}
