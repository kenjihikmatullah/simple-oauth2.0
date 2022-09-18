package com.khash.simpleoauth20.service.github;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khash.simpleoauth20.model.github.GetAccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class GetAccessTokenService {

    @Autowired
    Environment env;

    public String execute(String authCode) {
        RestTemplate restTemplate = new RestTemplate();

        String clientId = env.getProperty("oauth2.asClient.github.clientId");
        String clientSecret = env.getProperty("oauth2.asClient.github.clientSecret");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("code", authCode);


        String bodyStr = null;
        try {
            bodyStr = new ObjectMapper().writeValueAsString(body);
            System.out.println(bodyStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(bodyStr, headers);
        System.out.println("Request entity: " + requestEntity);

        ResponseEntity<GetAccessTokenResponse> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(
                    "https://github.com/login/oauth/access_token",
                    requestEntity,
                    GetAccessTokenResponse.class
            );

        } catch (HttpClientErrorException e) {
            System.out.println(e.getStatusCode());
            System.out.println(e.getResponseBodyAsString());
            e.printStackTrace();
        }

        System.out.println("Get access token http status: " + responseEntity.getStatusCode());
        System.out.println("Access token: " + responseEntity.getBody().accessToken);

        return Objects.requireNonNull(responseEntity.getBody()).accessToken;
    }
}
