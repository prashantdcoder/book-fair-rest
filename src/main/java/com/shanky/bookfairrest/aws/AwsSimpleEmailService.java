package com.shanky.bookfairrest.aws;

import com.shanky.bookfairrest.DTO.EmailDTO;
import com.shanky.bookfairrest.DTO.ResponseDTO;
import com.shanky.bookfairrest.exceptions.EmailSendFailedException;

public interface AwsSimpleEmailService {

    ResponseDTO<String> send(EmailDTO emailDTO) throws EmailSendFailedException;
}
