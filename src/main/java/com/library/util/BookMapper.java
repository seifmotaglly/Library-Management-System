package com.library.util;

import java.lang.reflect.Field;
import org.springframework.stereotype.Component;
import com.library.entity.Book;
import com.library.model.BookRequest;
import com.library.model.BookResponse;

@Component
public class BookMapper {

    public Book toBook(BookRequest bookRequest) {
        return Book.builder()
                .bookId(bookRequest.bookId())
                .bookTitle(bookRequest.bookTitle())
                .bookAuthor(bookRequest.bookAuthor())
                .bookPublicationDate(bookRequest.bookPublicationDate())
                .bookIsbn(bookRequest.bookIsbn())
                .borrowable(true)
                .build();
    }

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .bookId(book.getBookId())
                .bookAuthor(book.getBookAuthor())
                .bookTitle(book.getBookTitle())
                .bookPublicationDate(book.getBookPublicationDate())
                .bookIsbn(book.getBookIsbn())
                .borrowable(book.isBorrowable())
                .build();
            }
            //handel exception

    public void updateBook(Book book, BookRequest bookRequest) {
        try {
            Book updatedBook = this.toBook(bookRequest);
            Class<?> bookClass = Book.class;
            Field[] bookfields = bookClass.getDeclaredFields();
            for (Field field : bookfields) {
                field.setAccessible(true);
                Object value = field.get(updatedBook);
                if (value != null) {
                    field.set(book, value);
                }
                field.setAccessible(false);
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Failed to update book", e);
        }
    }

}
