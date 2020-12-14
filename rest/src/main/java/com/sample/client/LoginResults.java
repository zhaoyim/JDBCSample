package com.sample.client;

import static java.util.Objects.requireNonNull;

public class LoginResults {
    private int expires;
    private String accessToken;
    private String user;
    private String code;
    private String msg;

    public LoginResults(int expires, String accessToken, String user, String code, String msg) {
        this.expires = requireNonNull(expires, "expires is null");
        this.accessToken = requireNonNull(accessToken, "accessToken is null");
        this.user = requireNonNull(user, "user is null");
        this.code = requireNonNull(code, "code is null");
        this.msg = msg;
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

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
