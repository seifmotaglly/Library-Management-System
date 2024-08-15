package com.library.service;

import org.springframework.stereotype.Service;
import com.library.dao.BookDao;
import com.library.entity.Book;
import com.library.entity.Patron;
import com.library.model.BookRequest;
import com.library.model.BookResponse;
import com.library.util.BookMapper;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import org.springframework.security.core.Authentication;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDao bookDao;
    private final BookMapper bookMapper;

    @Transactional
    public Integer saveBook(BookRequest bookRequest, Authentication authenticatedPatron) {

        Patron patron = (Patron) authenticatedPatron.getPrincipal();
        Book book = bookMapper.toBook(bookRequest);
        book.setBookPatron(patron);
        bookDao.save(book);
        return book.getBookId();

    }

    public BookResponse findBookById(Integer bookId) {
        return bookDao.findById(bookId).map(bookMapper::toBookResponse)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public List<BookResponse> findAllBooks() {
        return bookDao.findAll().stream().map(bookMapper::toBookResponse).toList();
    }

    @Transactional
    public BookResponse updateBook(Integer bookId, @Valid BookRequest bookRequest, Authentication authenticatedPatron) {
        Book book = bookDao.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        Patron patron = (Patron) authenticatedPatron.getPrincipal();
        if (!patron.getPatronId().equals(book.getBookPatron().getPatronId())) {
            throw new RuntimeException("You cannot update this book");
        }
        
        bookMapper.updateBook(book, bookRequest);
        return bookMapper.toBookResponse(book);
    }

    @Transactional
    public Integer deleteBook(Integer bookId, Authentication authenticatedPatron ) {
        Book book = bookDao.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.isBorrowable()) {
            throw new RuntimeException("Book is already borrowed");
        }
        Patron patron = (Patron) authenticatedPatron.getPrincipal();
        if (!patron.getPatronId().equals(book.getBookPatron().getPatronId())) {
            throw new RuntimeException("You cannot delete this book");
        }
        bookDao.deleteById(bookId);
        return book.getBookId();
    }

    @Transactional
    public BookResponse putBook(Integer bookId, @Valid BookRequest bookRequest, Authentication authenticatedPatron) {
        Book book = bookDao.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.isBorrowable()) {
            throw new RuntimeException("Book is already borrowed");
        }
        Patron patron = (Patron) authenticatedPatron.getPrincipal();
        if (!patron.getPatronId().equals(book.getBookPatron().getPatronId())) {
            throw new RuntimeException("You cannot update this book");
        }
        book.setBookAuthor(bookRequest.bookAuthor());
        book.setBookTitle(bookRequest.bookTitle());
        book.setBookPublicationDate(bookRequest.bookPublicationDate());
        book.setBookIsbn(bookRequest.bookIsbn());
        return bookDao.findById(bookId).map(bookMapper::toBookResponse)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

}
