package com.mark.community.utils;

import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class IdempotencyUtil {
    private static Map<String, ResponseEntity<?>> idempotencyKeys = new ConcurrentHashMap<>();
    private static final Map<String, Long> expireTimes = new ConcurrentHashMap<>();
    private static final long EXPIRE_TIME = TimeUnit.MINUTES.toMillis(10);

    public static void setResponse(String idempotencyKey , ResponseEntity<?> responseEntity){
        if(idempotencyKey == null || idempotencyKey.isBlank()) return;
        idempotencyKeys.put(idempotencyKey, responseEntity);
        expireTimes.put(idempotencyKey, System.currentTimeMillis() + EXPIRE_TIME);
    }

    public static ResponseEntity<?> getResponse(String idempotencyKey){
        if(idempotencyKey == null || idempotencyKey.isBlank()) return null;
        Long expire = expireTimes.get(idempotencyKey);
        if(expire != null && expire < System.currentTimeMillis()) {
            idempotencyKeys.remove(idempotencyKey);
            expireTimes.remove(idempotencyKey);
            return null;
        }

        return idempotencyKeys.get(idempotencyKey);
    }
}
