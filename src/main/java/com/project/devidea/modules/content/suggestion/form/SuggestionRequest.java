package com.project.devidea.modules.content.suggestion.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SuggestionRequest {

    private String subject;
    private String message;

}
