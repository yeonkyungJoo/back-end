package com.project.devidea.modules.account.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorFieldAndMessage {

    private String field;
    private String message;
}
