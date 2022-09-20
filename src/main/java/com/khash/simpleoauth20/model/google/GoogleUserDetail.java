package com.khash.simpleoauth20.model.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleUserDetail {

    public String login;
    public String avatar_url;
    public String name;
    public String email;
}
