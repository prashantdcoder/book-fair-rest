package com.shanky.bookfairrest.controller;

import com.shanky.bookfairrest.CO.BookCO;
import com.shanky.bookfairrest.DTO.ResponseDTO;
import com.shanky.bookfairrest.VO.BookVO;
import com.shanky.bookfairrest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDTO<List<BookVO>>> index(@NotNull Authentication authentication) {
        ResponseDTO<List<BookVO>> responseDTO = bookService.fetchBooks();
        responseDTO.setSuccessResponse(responseDTO.getData(), null);
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN','AUTHOR')")
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDTO<String>> add(@Valid BookCO bookCO, BindingResult result, @NotNull Authentication authentication) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getField() + " " + result.getFieldError().getDefaultMessage();
            responseDTO.setFailureResponse(null, errorMessage);
        } else {
            responseDTO = bookService.add(authentication.getName(), bookCO);
        }
        return ResponseEntity.ok(responseDTO);
    }

}
