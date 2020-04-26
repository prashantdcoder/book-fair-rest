package com.shanky.bookfairrest.service;


import com.shanky.bookfairrest.DTO.ResponseDTO;

public interface InvitationService {

    ResponseDTO<String> sendInvitation(String email);
}
