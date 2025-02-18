package com.project.devidea.infra.config.security.oauth;

import com.project.devidea.modules.account.dto.LoginOAuthRequestDto;
import com.project.devidea.modules.account.dto.SignUpOAuthRequestDto;
import com.project.devidea.modules.account.dto.SignUpResponseDto;

import java.util.Map;

public interface OAuthServiceInterface {

    SignUpResponseDto signUpOAuth(SignUpOAuthRequestDto signUpOAuthRequestDto);

    Map<String, String> loginOAuth(LoginOAuthRequestDto loginOAuthRequestDto) throws Exception;
}
