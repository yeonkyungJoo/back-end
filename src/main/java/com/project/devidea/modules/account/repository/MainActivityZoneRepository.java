package com.project.devidea.modules.account.repository;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.MainActivityZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MainActivityZoneRepository extends JpaRepository<MainActivityZone, Long> {

    @Modifying
    @Query("delete from MainActivityZone m where m.account = :account")
    int deleteByAccount(@Param("account") Account account);

    List<MainActivityZone> findByAccount(Account account);

}
