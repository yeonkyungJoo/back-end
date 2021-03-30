package com.project.devidea.modules.content.study.notification;

import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.content.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyNotification {
    public void send(Study study, JpaRepository jpaRepository);
}
