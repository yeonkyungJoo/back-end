package com.project.devidea.modules.account;

import com.project.devidea.modules.account.dto.AccountProfileUpdateRequestDto;
import com.project.devidea.modules.account.dto.UpdatePasswordRequestDto;
import org.apache.tomcat.util.buf.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void 프로필_업데이트() throws Exception {

//        given
        Account account = AccountDummy.getAccount();
        AccountProfileUpdateRequestDto accountProfileUpdateRequestDto =
                AccountDummy.getAccountProfileUpdateRequestDto();

//        when
        account.updateProfile(accountProfileUpdateRequestDto);

//        then
        String techStacks = account.getTechStacks();
        assertAll(
                () -> assertEquals(account.getBio(), accountProfileUpdateRequestDto.getBio()),
                () -> assertEquals(account.getCareerYears(), accountProfileUpdateRequestDto.getCareerYears()),
                () -> assertEquals(account.getGender(), accountProfileUpdateRequestDto.getGender()),
                () -> assertEquals(account.getUrl(), accountProfileUpdateRequestDto.getUrl()),
                () -> assertEquals(account.getJob(), accountProfileUpdateRequestDto.getJob()),
                () -> assertEquals(account.getProfileImage(), accountProfileUpdateRequestDto.getProfileImage()),
                () -> assertEquals(techStacks, StringUtils.join(accountProfileUpdateRequestDto.getTechStacks(), '/')));
    }

    @Test
    void 비밀번호_변경() throws Exception {

//        given
        Account account = AccountDummy.getAccount();
        UpdatePasswordRequestDto updatePasswordRequestDto = AccountDummy.getUpdatePassowordRequestDto();

//        when
        account.updatePassword(updatePasswordRequestDto.getPassword());

//        then
        assertEquals(account.getPassword(), updatePasswordRequestDto.getPassword());
    }

}