package com.project.devidea.modules.account.repository;

import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.QAccount;
import com.project.devidea.modules.account.QInterest;
import com.project.devidea.modules.account.QMainActivityZone;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.project.devidea.modules.account.QAccount.*;
import static com.project.devidea.modules.account.QInterest.*;
import static com.project.devidea.modules.account.QMainActivityZone.*;

@RequiredArgsConstructor
public class AccountRepositoryCustomImpl implements AccountRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Account findByEmailWithMainActivityZoneAndInterests(String email) {
        return query.selectFrom(account)
                .leftJoin(account.interests, interest)
                .leftJoin(account.mainActivityZones, mainActivityZone)
                .fetchJoin()
                .where(account.email.eq(email))
                .fetchOne();
    }

    @Override
    public Account findByNicknameWithMainActivityZoneAndInterests(String nickname) {
        return query.selectFrom(account)
                .leftJoin(account.interests, interest)
                .leftJoin(account.mainActivityZones, mainActivityZone)
                .fetchJoin()
                .where(account.email.eq(nickname))
                .fetchOne();
    }
}
