package com.shanky.bookfairrest.service;

import com.shanky.bookfairrest.CO.BookCO;
import com.shanky.bookfairrest.DTO.ResponseDTO;
import com.shanky.bookfairrest.VO.BookVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookService {

    ResponseDTO<String> add(String username, BookCO bookCO);

    ResponseDTO<List<BookVO>> fetchBooks();
}
