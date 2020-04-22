package com.shanky.bookfairrest.service;

import com.shanky.bookfairrest.CO.BookCO;
import com.shanky.bookfairrest.DTO.ResponseDTO;
import org.springframework.stereotype.Component;

@Component
public interface BookService {

    ResponseDTO<String> add(String username, BookCO bookCO);
}
