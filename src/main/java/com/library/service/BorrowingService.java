package com.library.service;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.library.dao.BookDao;
import com.library.dao.BorrowingDao;
import com.library.dao.PatronDao;
import com.library.entity.Book;
import com.library.entity.Borrowing;
import com.library.entity.Patron;
import com.library.exception.OperationNotPermittedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Service
public class BorrowingService {

    private final BorrowingDao borrowingDao;
    private final BookDao bookDao;
    private final PatronDao patronDao;

    public Integer borrowBook(Integer bookId, Authentication authenticatedPatron) {
        // TODO:error handling
        Book book = bookDao.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        if (!book.isBorrowable()) {
            throw new OperationNotPermittedException("Book is already borrowed");
        }
        Patron patron = (Patron) authenticatedPatron.getPrincipal();
        if (patron.getPatronId().equals(book.getBookPatron().getPatronId())) {
            throw new OperationNotPermittedException("You cannot borrow your book");
        }
        final boolean isAlreadyBorrowedByUser = borrowingDao.isAlreadyBorrowedByUser(bookId, patron.getPatronId());
        if (isAlreadyBorrowedByUser) {
            throw new OperationNotPermittedException("You already borrowed this book");
        }
        book.setBorrowable(false);
        book.setBorrowedBy(patron.getPatronId());
        book.setBorrowedAt(LocalDateTime.now());
        Borrowing borrowingRecord = Borrowing.builder()
                .book(book)
                .patron(book.getBookPatron())
                .borrowedAt(LocalDateTime.now())
                .borrowedBy(patron.getPatronId())
                .borrowed(true)
                .build();
        bookDao.save(book);
        return borrowingDao.save(borrowingRecord).getBorrowingId();
         
    }

    public Integer returnBook(Integer bookId, Authentication authenticatedPatron) {
        // TODO:error handling
        Book book = bookDao.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        if (book.isBorrowable()) {
            throw new OperationNotPermittedException("Book is already returned");
        }
        Patron patron = (Patron) authenticatedPatron.getPrincipal();
        if (patron.getPatronId().equals(book.getBookPatron().getPatronId())) {
            throw new OperationNotPermittedException("You cannot return this book");
        }
        Borrowing borrowingRecord = borrowingDao.findByBookIdAndPatronId(bookId, book.getBookPatron().getPatronId())
                .orElseThrow(() -> new EntityNotFoundException("Borrowing record not found"));
        
        borrowingRecord.setBorrowed(false);
        book.setBorrowable(true);
        book.setBorrowedBy(null);
        book.setReturnedAt(LocalDateTime.now());
        borrowingRecord.setReturnedAt(LocalDateTime.now());
        borrowingRecord.setBorrowedBy(patron.getPatronId());
        bookDao.save(book);
        return borrowingDao.save(borrowingRecord).getBorrowingId();
    }

}
