package com.shanky.bookfairrest.controller;

import com.shanky.bookfairrest.DTO.ResponseDTO;
import com.shanky.bookfairrest.VO.UserVO;
import com.shanky.bookfairrest.domain.User;
import com.shanky.bookfairrest.repository.UserRepository;
import com.shanky.bookfairrest.security.JwtAuthenticationProvider;
import com.shanky.bookfairrest.service.CustomUserDetailService;
import com.shanky.bookfairrest.utils.AppUtil;
import com.shanky.bookfairrest.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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


    @RequestMapping(value = "/", method = RequestMethod.GET, consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDTO<String>> index() {
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
    ResponseEntity<ResponseDTO<UserVO>> authenticate(String username, String password) {
        ResponseDTO<UserVO> responseDTO = new ResponseDTO<>();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            String jwtToken = jwtAuthenticationProvider.generateToken(userDetails);
            Collection<? extends GrantedAuthority> roleList = userDetails.getAuthorities();
            List<String> roleListInString = roleList.stream().map(it -> ((GrantedAuthority) it).getAuthority()).collect(Collectors.toList());
            UserVO userVO = new UserVO(username, jwtToken, roleListInString, AppUtil.parseDateInString(jwtAuthenticationProvider.extractExpiration(jwtToken)));
            responseDTO.setSuccessResponse(userVO, StringUtil.LOGIN_SUCCESS);
        } catch (BadCredentialsException e) {
            responseDTO.setFailureResponse(null, e.getMessage());
        }
        return ResponseEntity.ok(responseDTO);
    }
}
