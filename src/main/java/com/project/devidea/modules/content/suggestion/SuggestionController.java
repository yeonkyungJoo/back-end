package com.project.devidea.modules.content.suggestion;

import com.project.devidea.infra.config.security.CurrentUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.content.mentoring.exception.NotFoundException;
import com.project.devidea.modules.content.suggestion.form.SuggestionRequest;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/suggestion")
@RequiredArgsConstructor
public class SuggestionController {

    private final SuggestionRepository suggestionRepository;
    private final SuggestionService suggestionService;

    @ApiOperation("내가 받은/보낸 제안 전체 조회")
    @GetMapping("/")
    public ResponseEntity getSuggestions(@CurrentUser Account account) {

        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        List<SuggestionDto> suggestions = suggestionRepository.findByAccount(account).stream()
                .map(suggestion -> new SuggestionDto(suggestion))
                .collect(Collectors.toList());
        return new ResponseEntity(suggestions, HttpStatus.OK);
    }

    @ApiOperation("제안 조회")
    @GetMapping("/{id}")
    public ResponseEntity getSuggestion(@CurrentUser Account account,
                                        @PathVariable(name = "id") Long suggestionId) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Suggestion suggestion = suggestionRepository.findById(suggestionId)
                .orElseThrow(() -> new NotFoundException());
        return new ResponseEntity(new SuggestionDto(suggestion), HttpStatus.OK);
    }

    @ApiOperation("제안하기")
    @PostMapping("/{id}")
    public ResponseEntity suggest(@CurrentUser Account account, @PathVariable(name = "id") Long id,
                                  @RequestBody @Valid SuggestionRequest request) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        Long suggestionId = suggestionService.suggest(account, id, request);
        return new ResponseEntity(suggestionId, HttpStatus.CREATED);
    }

    @ApiOperation("보낸 제안 취소")
    @PostMapping("/{id}/cancel")
    public ResponseEntity cancelSuggestion(@CurrentUser Account account,
                                           @PathVariable(name = "id") Long suggestionId) {
        if (account == null) {
            throw new AccessDeniedException("Access is Denied");
        }
        suggestionService.cancel(account, suggestionId);
        return new ResponseEntity(HttpStatus.OK);
    }


    @Data
    class SuggestionDto {

        private Long id;
        private String dateTime;
        private String nameFrom;
        private String emailFrom;
        private String nameTo;
        private String emailTo;
        private String subject;
        private String message;

        public SuggestionDto(Suggestion suggestion) {
            this.id = suggestion.getId();
            this.dateTime = suggestion.getDateTime().toString();
            this.nameFrom = suggestion.getFrom().getName();
            this.emailFrom = suggestion.getFrom().getEmail();
            this.nameTo = suggestion.getTo().getName();
            this.emailTo = suggestion.getTo().getEmail();
            this.subject = suggestion.getSubject();
            this.message = suggestion.getMessage();
        }

    }
}
