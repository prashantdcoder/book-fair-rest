package com.shanky.bookfairrest.repository;

import com.shanky.bookfairrest.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
