package com.project.devidea.infra.config.security.oauth.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoogleOAuth implements SocialOAuth {

    private final String GOOGLE_SNS_BASE_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private final String GOOGLE_SNS_CLIENT_ID = "9719839326-fnc7hcgbq8nit8qp6s3ip7vrfn01gche.apps.googleusercontent.com";
    private final String GOOGLE_SNS_CALLBACK_URL = "http://localhost:8080/login/oauth/google/callback";
    private final String GOOGLE_SNS_CLIENT_SECRET = "k64ajtdh7iPfaEkW_TjbwiGm";
    private final String GOOGLE_SNS_TOKEN_BASE_URL = "https://oauth2.googleapis.com/token";
    private final String GOOGLE_SNS_ACCESS_USERINFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";
    private final ObjectMapper objectMapper;

    @Override
    public Map<String, String> getOAuthRedirectURL() {
        Map<String, String> map = new HashMap<>();
        map.put("base_url", GOOGLE_SNS_BASE_URL);
        map.put("scope", "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile");
        map.put("response_type", "code");
        map.put("client_id", GOOGLE_SNS_CLIENT_ID);
        map.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        return map;
    }

    @Override
    public Map<String, String> requestLogin(String code) {
        String accessToken = requestAccessToken(code);
        String userInfo = requestUserInfo(accessToken);
        return convertStringToMap(userInfo);
    }

    private Map<String, String> convertStringToMap(String convert) {
        Map<String, String> map = null;
        try {
            map = objectMapper.readValue(convert, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public String requestUserInfo(String accessToken) {
        Map<String, String> map = convertStringToMap(accessToken);

        String getAccessToken = map.get("access_token");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getAccessToken);
        HttpEntity entity = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(GOOGLE_SNS_ACCESS_USERINFO_URL, HttpMethod.GET, entity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }

        return "fail OAuth2 Login";
    }

    @Override
    public String requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }

        return "fail OAuth2 Login";   //실패시 어떤 것을 반환해주어야 하는지?
    }
}
