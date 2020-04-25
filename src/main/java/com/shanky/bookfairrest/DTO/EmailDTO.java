package com.shanky.bookfairrest.DTO;

public class EmailDTO {

    private String receiver;

    private String textBody;
    private String htmlBody;
    private String subject;

    public EmailDTO() {

    }

    public EmailDTO(String subject, String receiver) {
        this.subject = subject;
        this.receiver = receiver;
    }

    public void setHtmlTemplate(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public void sePlainTextTemplate(String textBody) {
        this.textBody = textBody;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public String getTextBody() {
        return this.textBody;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getHtmlBody() {
        return this.htmlBody;
    }

}
