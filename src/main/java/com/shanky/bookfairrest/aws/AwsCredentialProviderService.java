package com.shanky.bookfairrest.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AwsCredentialProviderService implements AWSCredentialsProvider {

    @Value("${aws.config.key}")
    private String key;

    @Value("${aws.config.secret}")
    private String secret;

    @Value("${aws.config.sender.email}")
    private String sender;

    @Override
    public AWSCredentials getCredentials() {
        return new BasicAWSCredentials(key, secret);
    }

    @Override
    public void refresh() {

    }

    public String getSenderEmail() {
        return this.sender;
    }
}
