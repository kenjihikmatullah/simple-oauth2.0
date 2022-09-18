package com.khash.simpleoauth20.model.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetail {

    public String login;
    public String avatar_url;
    public String name;
    public String email;
}
