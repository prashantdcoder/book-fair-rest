package com.shanky.bookfairrest.controller;

import com.shanky.bookfairrest.DTO.ResponseDTO;
import com.shanky.bookfairrest.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    InvitationService invitationService;

    @RequestMapping(value = "/invite", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<String>> invite(String email) {
        ResponseDTO<String> responseDTO = invitationService.sendInvitation(email);
        return ResponseEntity.ok(responseDTO);
    }

}
