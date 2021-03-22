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
public class GithubOAuth implements SocialOAuth {

    private final String GITHUB_SNS_BASE_URL = "https://github.com/login/oauth/authorize";
    private final String GITHUB_SNS_CLIENT_ID = "9bc5902314cd6dbcb181";
    private final String GITHUB_SNS_CALLBACK_URL = "http://localhost:8080/login/oauth/github/callback";
    private final String GITHUB_SNS_CLIENT_SECRET = "546cd1a7649e1b134d6c8651d486609493984e25";
    private final String GITHUB_SNS_TOKEN_BASE_URL = "https://github.com/login/oauth/access_token";
    private final String GITHUB_SNS_ACCESS_USERINFO_URL = "https://api.github.com/user";
    private final ObjectMapper objectMapper;

    @Override
    public Map<String, String> getOAuthRedirectURL() {
        Map<String, String> map = new HashMap<>();
        map.put("scope", "read:user%20user:email");
        map.put("client_id", GITHUB_SNS_CLIENT_ID);
        map.put("redirect_uri", GITHUB_SNS_CALLBACK_URL);
        map.put("base_url", GITHUB_SNS_BASE_URL);
        return map;
    }

    @Override
    public String requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GITHUB_SNS_CLIENT_ID);
        params.put("client_secret", GITHUB_SNS_CLIENT_SECRET);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(GITHUB_SNS_TOKEN_BASE_URL, entity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }

        return "fail OAuth2 Login";   //실패시 어떤 것을 반환해주어야 하는지?
    }

    @Override
    public String requestUserInfo(String accessToken) {
        Map<String, String> map = convertStringToMap(accessToken);

        String getAccessToken = map.get("access_token");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + getAccessToken);
        HttpEntity entity = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(GITHUB_SNS_ACCESS_USERINFO_URL, HttpMethod.GET, entity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }

        return "fail OAuth2 Login";
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
    public Map<String, String> requestLogin(String code) {
        String accessToken = requestAccessToken(code);
        String userInfo = requestUserInfo(accessToken);
        return convertStringToMap(userInfo);
    }
}
