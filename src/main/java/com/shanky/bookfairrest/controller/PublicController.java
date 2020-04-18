package com.shanky.bookfairrest.controller;

import com.shanky.bookfairrest.DTO.ResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PublicController {

    @RequestMapping(value = "/", method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDTO<String>> index(String name) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setSuccessResponse("Welcome to book-fair-rest-application", null);
        return ResponseEntity.ok(responseDTO);
    }

}
