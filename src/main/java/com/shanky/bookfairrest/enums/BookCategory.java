package com.shanky.bookfairrest.enums;

public enum BookCategory {

    LITERATURE("Literature"),
    SCIENCE_FICTION("Sci-Fi"),
    ART("Art and Photography"),
    HISTORY("History"),
    ROMANCE("Romance"),
    ADULT("Adult");

    String value;

    BookCategory(String value) {
        this.value = value;
    }
}
