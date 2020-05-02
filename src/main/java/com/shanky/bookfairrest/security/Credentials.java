package com.shanky.bookfairrest.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@PropertySource("file:C:/Users/Prashant/bookfair/config/customProperties.properties")
public class Credentials {


    private String name;

    private String nickName;

    private Aws aws;

    private Jwt jwt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Aws getAws() {
        return aws;
    }

    public void setAws(Aws aws) {
        this.aws = aws;
    }

    public Jwt getJwt() {
        return jwt;
    }

    public void setJwt(Jwt jwt) {
        this.jwt = jwt;
    }

    /*It is required to access nested properties mentioned in properties file*/
    public static class Aws {

        private String key;
        private String secret;
        private String senderEmail;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getSenderEmail() {
            return senderEmail;
        }

        public void setSenderEmail(String senderEmail) {
            this.senderEmail = senderEmail;
        }
    }

    public static class Jwt {

        private String tokenSecretKey;
        private Long tokenExpireLength;

        public String getTokenSecretKey() {
            return tokenSecretKey;
        }

        public void setTokenSecretKey(String tokenSecretKey) {
            this.tokenSecretKey = tokenSecretKey;
        }

        public Long getTokenExpireLength() {
            return tokenExpireLength;
        }

        public void setTokenExpireLength(Long tokenExpireLength) {
            this.tokenExpireLength = tokenExpireLength;
        }
    }
}
