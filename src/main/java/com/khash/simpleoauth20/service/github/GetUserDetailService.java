package com.khash.simpleoauth20.service.github;

import com.khash.simpleoauth20.model.github.UserDetail;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class GetUserDetailService {

    public UserDetail execute(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<UserDetail> responseEntity = restTemplate.exchange(
                "https://api.github.com/user",
                HttpMethod.GET,
                entity,
                UserDetail.class
        );

        System.out.println("Get user detail http status: " + responseEntity.getStatusCode());
        System.out.println("Email: " + responseEntity.getBody().email);

        return responseEntity.getBody();
    }
}
