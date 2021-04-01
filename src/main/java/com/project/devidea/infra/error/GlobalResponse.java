package com.project.devidea.infra.error;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalResponse {

    private int statusCode;
    private LocalDateTime time;
    private String description;
    private Object data;

    public static GlobalResponse of(){
        return getGlobalResponse();
    }

    public static GlobalResponse of(Object data) {
        return getGlobalResponse(data);
    }

    private static GlobalResponse getGlobalResponse(){
        return GlobalResponse.builder()
                .time(LocalDateTime.now())
                .description("요청이 정상적으로 처리되었습니다.")
                .statusCode(HttpStatus.OK.value())
                .data("")
                .build();
    }

    private static GlobalResponse getGlobalResponse(Object data){
        return GlobalResponse.builder()
                .time(LocalDateTime.now())
                .description("요청이 정상적으로 처리되었습니다.")
                .statusCode(HttpStatus.OK.value())
                .data(data)
                .build();
    }
}
