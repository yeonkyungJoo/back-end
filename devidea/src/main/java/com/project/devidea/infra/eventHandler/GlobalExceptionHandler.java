package com.project.devidea.infra.eventHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Map<String, String> handleException(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("오류가 발생했습니다.", e.getMessage());
        return map;
    }
}
