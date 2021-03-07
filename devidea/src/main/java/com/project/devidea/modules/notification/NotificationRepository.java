package com.project.devidea.modules.notification;

import com.project.devidea.modules.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Transactional
    List<Notification> findByAccountAndCheckedOrderByCreatedDateTimeDesc(Account account, boolean checked);
}
