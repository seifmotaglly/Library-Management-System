package com.library.model;

// TODO: null handling and validation
public record BookRequest(
                Integer bookId,
                // @NotNull(message = "Book title cannot be null")
                // @NotEmpty(message = "Book title cannot be empty")
                String bookTitle,
                // @NotNull(message = "Book author cannot be null")
                // @NotEmpty(message = "Book author cannot be empty")
                String bookAuthor,
                // @NotNull(message = "Book publication date cannot be null")
                // @NotEmpty(message = "Book publication date cannot be empty")
                String bookPublicationDate,
                // @NotNull(message = "Book Isbn cannot be null")
                int bookIsbn
                ) {

}