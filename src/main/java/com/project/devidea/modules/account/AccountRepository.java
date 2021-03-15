package com.project.devidea.modules.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNickname(String nickname);

    Optional<Account> findByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);
}
