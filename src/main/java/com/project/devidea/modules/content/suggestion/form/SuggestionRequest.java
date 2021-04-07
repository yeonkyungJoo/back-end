package com.project.devidea.modules.content.suggestion.form;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class SuggestionRequest {

    @NotEmpty
    private String subject;
    @NotEmpty
    private String message;

    @Builder
    public SuggestionRequest(@NotEmpty String subject, @NotEmpty String message) {
        this.subject = subject;
        this.message = message;
    }
}
