package com.project.devidea.infra.config.security.oauth;

import com.project.devidea.modules.account.form.LoginOAuthRequestDto;
import com.project.devidea.modules.account.form.LoginRequestDto;
import com.project.devidea.modules.account.form.SignUpOAuthRequestDto;
import com.project.devidea.modules.account.form.SignUpResponseDto;

import java.util.Map;

public interface OAuthServiceInterface {

    SignUpResponseDto signUpOAuth(SignUpOAuthRequestDto signUpOAuthRequestDto);

    Map<String, String> loginOAuth(LoginOAuthRequestDto loginOAuthRequestDto) throws Exception;
}
