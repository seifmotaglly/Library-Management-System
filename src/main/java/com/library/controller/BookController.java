package com.library.controller;

import org.springframework.web.bind.annotation.RestController;
import com.library.model.BookRequest;
import com.library.model.BookResponse;
import com.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @PostMapping
    public ResponseEntity<Integer> saveBook(@Valid @RequestBody BookRequest bookRequest, Authentication authenticatedPatron) {
        return ResponseEntity.ok(bookService.saveBook(bookRequest, authenticatedPatron)); 
    }
    
    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable("bookId") Integer bookId) {
        return ResponseEntity.ok(bookService.findBookById(bookId));
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable("bookId") Integer bookId, @Valid @RequestBody BookRequest bookRequest, Authentication authenticatedPatron) {
        return ResponseEntity.ok(bookService.updateBook(bookId, bookRequest, authenticatedPatron));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookResponse> putBook(@PathVariable("bookId") Integer bookId, @Valid @RequestBody BookRequest bookRequest, Authentication authenticatedPatron) {
        return ResponseEntity.ok(bookService.putBook(bookId, bookRequest, authenticatedPatron));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Integer> deleteBook(@PathVariable("bookId") Integer bookId, Authentication authenticatedPatron) {
        return ResponseEntity.ok(bookService.deleteBook(bookId, authenticatedPatron));
    }
    

}
