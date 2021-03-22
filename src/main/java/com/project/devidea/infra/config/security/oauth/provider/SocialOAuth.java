package com.project.devidea.infra.config.security.oauth.provider;

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

    Map<String, String> getOAuthRedirectURL();

    String requestAccessToken(String code);

    String requestUserInfo(String accessToken);

    Map<String, String> requestLogin(String code);
}
