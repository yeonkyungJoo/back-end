package com.project.devidea.modules.notification;

import com.project.devidea.modules.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Transactional(readOnly = true)
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    int countByAccountAndChecked(Account account, boolean checked);

    List<Notification> findByAccountAndCheckedOrderByCreatedDateTimeDesc(Account account, boolean checked);

    @Transactional
    void deleteByAccountAndChecked(Account account, boolean checked);
}
