package com.project.devidea.modules.account.repository;

import com.project.devidea.modules.account.Account;

import java.util.Optional;

public interface AccountRepositoryCustom {

    Account findByEmailWithMainActivityZoneAndInterests(String email);

    Account findByNicknameWithMainActivityZoneAndInterests(String nickname);
}
