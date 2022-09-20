package com.khash.simpleoauth20.service.google;

import com.khash.simpleoauth20.model.google.GoogleUserDetail;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class GoogleGetUserDetailService {

    public GoogleUserDetail execute(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GoogleUserDetail> responseEntity = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                HttpMethod.GET,
                entity,
                GoogleUserDetail.class
        );

        System.out.println("Get user detail http status: " + responseEntity.getStatusCode());
        System.out.println("Email: " + responseEntity.getBody().email);

        return responseEntity.getBody();
    }
}
