package com.shanky.bookfairrest.VO;

public class UserVO {

    private String username;
    private String accessToken;
    private String expiryDate;

    public UserVO(String username, String accessToken, String expiryDate) {
        this.username = username;
        this.accessToken = accessToken;
        this.expiryDate = expiryDate;
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
}
