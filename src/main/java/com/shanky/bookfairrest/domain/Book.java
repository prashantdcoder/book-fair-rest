package com.shanky.bookfairrest.domain;

import com.shanky.bookfairrest.enums.BookCategory;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;

    private String isbn;
    private Double price;
    private BookCategory category;

    private Double length;
    private Double breadth;
    private Double height;
    private Integer pages;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getBreadth() {
        return breadth;
    }

    public void setBreadth(Double breadth) {
        this.breadth = breadth;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
