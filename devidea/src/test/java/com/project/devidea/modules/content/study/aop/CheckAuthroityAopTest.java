package com.project.devidea.modules.content.study.aop;

import com.project.devidea.infra.config.security.LoginUser;
import com.project.devidea.modules.account.Account;
import com.project.devidea.modules.account.AccountRepository;
import com.project.devidea.modules.content.study.repository.StudyMemberRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class CheckAuthroityAopTest {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AopTestService aopTestService;
    @Test
    void CheckTest(){
        String nickName="DevIdea";
        Long id=1L;
        Account account=accountRepository.findByNickname(nickName);
        LoginUser user=new LoginUser(account);
        aopTestService.testFunction(user,id);
        aopTestService.testFunction(null,id);
    }
}