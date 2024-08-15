package com.library.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

    private Integer bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublicationDate;
    private int bookIsbn;
    private boolean borrowable;

}
