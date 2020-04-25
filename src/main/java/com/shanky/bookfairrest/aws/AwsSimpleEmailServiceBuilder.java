package com.shanky.bookfairrest.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.shanky.bookfairrest.DTO.EmailDTO;
import com.shanky.bookfairrest.DTO.ResponseDTO;
import com.shanky.bookfairrest.exceptions.EmailSendFailedException;
import org.springframework.stereotype.Service;

@Service
public class AwsSimpleEmailServiceBuilder implements AwsSimpleEmailService {


    private String sender;

    private AmazonSimpleEmailService awsClient;

    public AwsSimpleEmailServiceBuilder(AwsCredentialProviderService awsCredentialProviderService) {
        AWSStaticCredentialsProvider staticCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentialProviderService.getCredentials());
        this.awsClient = AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(staticCredentialsProvider)
                .withRegion(Regions.US_EAST_1).build();
        this.sender = awsCredentialProviderService.getSenderEmail();
    }

    @Override
    public ResponseDTO<String> send(EmailDTO emailDTO) throws EmailSendFailedException {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            System.out.println("..Aws email service has been initiated");
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(new Destination().withToAddresses(emailDTO.getReceiver()))
                    .withMessage(emailDTO.getHtmlBody() != null ? getMessageBodyWithHtml(emailDTO) : getMessageBodyWithText(emailDTO))
                    .withSource(sender);
            awsClient.sendEmail(request);
            System.out.println("Aws email has been sent!!");
            responseDTO.setSuccessResponse(null, "Email success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setFailureResponse(null, "Email failure");
            throw new EmailSendFailedException("Aws email was not sent");
        }
        return responseDTO;
    }

    private Message getMessageBodyWithHtml(EmailDTO emailDTO) {
        Body body = new Body();
        body.withHtml(new Content().withCharset("UTF-8").withData(emailDTO.getHtmlBody()));
        Message message = new Message();
        message.withBody(body).withSubject(new Content().withCharset("UTF-8").withData(emailDTO.getSubject()));
        return message;
    }

    private Message getMessageBodyWithText(EmailDTO emailDTO) {
        Body body = new Body();
        body.withText(new Content().withCharset("UTF-8").withData(emailDTO.getTextBody()));
        Message message = new Message();
        message.withBody(body).withSubject(new Content().withCharset("UTF-8").withData(emailDTO.getSubject()));
        return message;
    }

}