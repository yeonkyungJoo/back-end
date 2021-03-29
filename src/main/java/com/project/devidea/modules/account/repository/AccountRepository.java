package com.project.devidea.modules.account.repository;

import com.project.devidea.modules.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {
    Account findByNickname(String nickname);

    Optional<Account> findByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    @Query("select A.id from Account A where A.nickname=:nickname")
    Long findIdByNickname(@Param("nickname") String nickname);
}
