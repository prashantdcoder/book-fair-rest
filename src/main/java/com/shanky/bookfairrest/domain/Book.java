package com.shanky.bookfairrest.domain;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String title;
    private String description;

    private String isbn;
    private Double price;
    private String category;

    private Double length;
    private Double breadth;
    private Double height;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

}
