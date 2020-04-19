package com.shanky.bookfairrest.controller;

import com.shanky.bookfairrest.DTO.ResponseDTO;
import com.shanky.bookfairrest.constants.StringUtil;
import com.shanky.bookfairrest.domain.User;
import com.shanky.bookfairrest.repository.UserRepository;
import com.shanky.bookfairrest.security.JwtAuthenticationProvider;
import com.shanky.bookfairrest.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class PublicController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUserDetailService userDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtAuthenticationProvider jwtAuthenticationProvider;


    @RequestMapping(value = "/", method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDTO<String>> index(String name) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setSuccessResponse(StringUtil.WElCOME_NOTE, null);
        return ResponseEntity.ok(responseDTO);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDTO<String>> register(String username, String email, String password, String fullName) {
        User user = userRepository.findByUsername(username);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if (user != null) {
            responseDTO.setFailureResponse(null, StringUtil.USERNAME_EXISTS);
        } else {
            userDetailService.save(fullName, username, email, password);
            responseDTO.setSuccessResponse(StringUtil.REGISTRATION_DONE, null);
        }
        return ResponseEntity.ok(responseDTO);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDTO<Map<String, String>>> authenticate(String username, String password) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
        try {
            Map<String, String> map = new HashMap<>();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            String jwtToken = jwtAuthenticationProvider.generateToken(userDetails);
            map.put("username", username);
            map.put("accessToken", jwtToken);
            responseDTO.setSuccessResponse(map, StringUtil.LOGIN_SUCCESS);
        } catch (BadCredentialsException e) {
            responseDTO.setFailureResponse(null, e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }
}
