package com.shanky.bookfairrest.serviceImplementation;

import com.shanky.bookfairrest.CO.BookCO;
import com.shanky.bookfairrest.DTO.ResponseDTO;
import com.shanky.bookfairrest.domain.Book;
import com.shanky.bookfairrest.repository.BookRepository;
import com.shanky.bookfairrest.repository.UserRepository;
import com.shanky.bookfairrest.service.BookService;
import com.shanky.bookfairrest.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImp implements BookService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;


    @Override
    public ResponseDTO<String> add(String loggedInUser, BookCO bookCO) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            Book book = new Book();
            book.setTitle(bookCO.getTitle());
            book.setDescription(bookCO.getDescription());
            book.setIsbn(bookCO.getIsbn());
            book.setPrice(bookCO.getPrice());
            book.setLength(bookCO.getLength());
            book.setBreadth(bookCO.getBreadth());
            book.setHeight(bookCO.getHeight());
            book.setPages(bookCO.getPages());
            book.setCategory(bookCO.getCategory());
            book.setActive(true);
            book.setAuthor(userRepository.findByUsername(loggedInUser));
            bookRepository.save(book);
            responseDTO.setSuccessResponse(null, StringUtil.BOOK_SAVED);
        } catch (Exception e) {
            responseDTO.setFailureResponse(null, e.getMessage());
        }
        return responseDTO;
    }
}
