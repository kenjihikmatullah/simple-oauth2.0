package com.khash.simpleoauth20.model.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleGetAccessTokenResponse {

    @JsonProperty("access_token")
    public String accessToken;

    public String scope;

    @JsonProperty("token_type")
    public String tokenType;
}
