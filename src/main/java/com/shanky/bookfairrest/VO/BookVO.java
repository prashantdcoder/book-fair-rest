package com.shanky.bookfairrest.VO;

import com.shanky.bookfairrest.domain.Book;

public class BookVO {

    private Long id;
    private String title;
    private String author;
    private Double price;

    public BookVO() {

    }

    public BookVO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor().getFullName();
        this.price = book.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
