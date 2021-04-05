package com.project.devidea.infra.config.security.oauth;

import com.project.devidea.modules.account.dto.Login;
import com.project.devidea.modules.account.dto.SignUp;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface OAuthService {

    SignUp.Response signUpOAuth(SignUp.OAuthRequest request) throws NoSuchAlgorithmException;

    Map<String, String> loginOAuth(Login.OAuth loginOAuthRequestDto) throws Exception;
}
