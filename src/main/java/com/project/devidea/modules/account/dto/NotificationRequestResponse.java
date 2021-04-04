package com.project.devidea.modules.account.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestResponse {

    private boolean receiveEmail;
    private boolean receiveNotification;
    private boolean receiveTechNewsNotification;
    private boolean receiveMentoringNotification;
    private boolean receiveStudyNotification;
    private boolean receiveRecruitingNotification;
}
