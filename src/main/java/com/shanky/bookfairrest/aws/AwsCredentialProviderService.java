package com.shanky.bookfairrest.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.shanky.bookfairrest.security.Credentials;
import org.springframework.stereotype.Component;

@Component
public class AwsCredentialProviderService implements AWSCredentialsProvider {

    private String sender;

    private BasicAWSCredentials basicAWSCredentials;

    AwsCredentialProviderService(Credentials serviceCredentials) {
        this.sender = serviceCredentials.getAws().getSenderEmail();
        this.basicAWSCredentials = new BasicAWSCredentials(serviceCredentials.getAws().getKey(), serviceCredentials.getAws().getSecret());
    }

    @Override
    public AWSCredentials getCredentials() {
        return basicAWSCredentials;
    }

    @Override
    public void refresh() {

    }

    public String getSender() {
        return this.sender;
    }
}
