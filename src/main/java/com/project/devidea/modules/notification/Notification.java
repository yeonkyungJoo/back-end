package com.project.devidea.modules.notification;


import com.project.devidea.modules.account.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String message;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    private boolean checked;

    private LocalDateTime createdDateTime;
    private LocalDateTime checkedDateTime;

    public void markAsRead() {
        if(!this.checked) {
            this.checked = true;
            checkedDateTime = LocalDateTime.now();
        }
    }
}
