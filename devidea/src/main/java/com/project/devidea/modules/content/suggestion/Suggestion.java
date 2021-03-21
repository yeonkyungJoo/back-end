package com.project.devidea.modules.content.suggestion;

import com.project.devidea.modules.account.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Suggestion {

    @Id @GeneratedValue
    @Column(name = "suggestion_id")
    private Long id;

    private LocalDateTime date;

/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account to;*/

    private String message;
}
