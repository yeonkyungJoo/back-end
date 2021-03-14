package com.project.devidea.infra.config.oauth.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.devidea.infra.config.oauth.provider.GoogleOAuth;
import com.project.devidea.infra.config.oauth.provider.SocialLoginType;

import java.util.Map;

public interface SocialOAuth {

    default SocialLoginType type() {
        if (this instanceof GoogleOAuth) {
            return SocialLoginType.GOOGLE;
        }
        if (this instanceof GithubOAuth) {
            return SocialLoginType.GITHUB;
        }
        return null;
    }

    String getOAuthRedirectURL();

    String requestAccessToken(String code);

    String requestUserInfo(String accessToken);

    Map<String, String> requestLogin(String code);
}
