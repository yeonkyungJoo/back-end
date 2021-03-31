package com.project.devidea.modules.content.study.notification;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.repository.AccountRepository;
import com.project.devidea.modules.content.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyNotification {
    public void sendRelated(Study study, JpaRepository jpaRepository);
    public void sendOwn(Study study, Account account, JpaRepository jpaRepository);
    public void sendAll(Study study,Account account,JpaRepository jpaRepository);
}
