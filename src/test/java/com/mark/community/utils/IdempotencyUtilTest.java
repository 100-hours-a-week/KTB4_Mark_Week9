package com.mark.community.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class IdempotencyUtilTest {

    @Test
    void 키가_null이면_저장하지_않는다(){
        IdempotencyUtil.setResponse(null, ResponseEntity.ok("응답A"));
        assertNull(IdempotencyUtil.getResponse(null));
    }

    @Test
    void 키가_빈문자열이면_저장하지_않는다(){
        IdempotencyUtil.setResponse("", ResponseEntity.ok("응답B"));
        assertNull(IdempotencyUtil.getResponse(""));
    }

    @Test
    void 헤더없는_요청들이_서로_영향을_주지_않는다(){
        IdempotencyUtil.setResponse(null, ResponseEntity.ok("첫번째글"));
        ResponseEntity<?> result = IdempotencyUtil.getResponse(null);
        assertNull(result);
    }

    @Test
    void 유효한_키로_저장하면_같은_키로_조회시_그대로_반환된다(){
        ResponseEntity<String> response = ResponseEntity.ok("멱등응답");
        IdempotencyUtil.setResponse("key1", response);

        assertEquals(response, IdempotencyUtil.getResponse("key1"));
    }

    @Test
    void 서로_다른_키는_독립적으로_저장된다(){
        IdempotencyUtil.setResponse("key1", ResponseEntity.ok("응답1"));
        IdempotencyUtil.setResponse("key2", ResponseEntity.ok("응답2"));

        assertEquals("응답1", IdempotencyUtil.getResponse("key1").getBody());
        assertEquals("응답2", IdempotencyUtil.getResponse("key2").getBody());
    }

}
