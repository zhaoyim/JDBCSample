package com.sample.client;

import static java.util.Objects.requireNonNull;

public class LoginResults {
    private int expires;
    private String accessToken;
    private String user;

    public LoginResults(int expires, String accessToken, String user) {
        this.expires = requireNonNull(expires, "expires is null");
        this.accessToken = requireNonNull(accessToken, "accessToken is null");
        this.user = requireNonNull(user, "user is null");
    }

    public int getExpires() {
        return expires;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUser() {
        return user;
    }
}
