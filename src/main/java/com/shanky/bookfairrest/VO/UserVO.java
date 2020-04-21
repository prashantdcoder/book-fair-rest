package com.shanky.bookfairrest.VO;

import java.util.List;

public class UserVO {

    private String username;
    private String accessToken;
    private String expiryDate;
    private List<String> roles;

    public UserVO(String username, String accessToken, List<String> roles, String expiryDate) {
        this.username = username;
        this.accessToken = accessToken;
        this.expiryDate = expiryDate;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
