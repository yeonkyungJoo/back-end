package com.project.devidea.modules.account.exception;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountErrorMessage {

    private int statusCode;
    private LocalDateTime time;
    private List<ErrorFieldAndMessage> errorFieldAndMessageList = new ArrayList<>();
    private String description;

}
