package com.shanky.bookfairrest.exceptions;

public class EmailSendFailedException extends Exception {

    public EmailSendFailedException(String message) {
        super(message);
    }
}
