package com.project.devidea.modules.content.suggestion;

import com.project.devidea.modules.account.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Suggestion {

    @Id @GeneratedValue
    @Column(name = "suggestion_id")
    private Long id;

    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from")
    private Account from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to")
    private Account to;

    private String subject;
    private String message;

    public static Suggestion createSuggestion(Account from, Account to, String subject, String message) {

        Suggestion suggestion = new Suggestion();
        suggestion.setFrom(from);
        suggestion.setTo(to);
        suggestion.setSubject(subject);
        suggestion.setMessage(message);

        suggestion.setDateTime(LocalDateTime.now());
        return suggestion;
    }
}
