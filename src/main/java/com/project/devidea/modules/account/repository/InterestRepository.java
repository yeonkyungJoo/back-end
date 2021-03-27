package com.project.devidea.modules.account.repository;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface InterestRepository extends JpaRepository<Interest, Long> {

    @Modifying
    @Query("delete from Interest i where i.account = :account")
    int deleteByAccount(@Param("account") Account account);

    List<Interest> findByAccount(Account account);
}
