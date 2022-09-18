package com.khash.simpleoauth20.model.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAccessTokenResponse {

    @JsonProperty("access_token")
    public String accessToken;

    public String scope;

    @JsonProperty("token_type")
    public String tokenType;
}
