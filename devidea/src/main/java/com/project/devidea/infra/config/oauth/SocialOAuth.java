package com.project.devidea.infra.config.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface SocialOAuth {

    default SocialLoginType type() {
        if (this instanceof GoogleOAuth) {
            return SocialLoginType.GOOGLE;
        } else {
            return null;
        }
    }

    String getOAuthRedirectURL();

    String requestAccessToken(String code);

    String requestUserInfo(String accessToken) throws JsonProcessingException;

    Map<String, String> requestLogin(String code);
}
