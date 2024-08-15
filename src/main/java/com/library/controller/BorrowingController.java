package com.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.library.service.BorrowingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingService borrowingService;

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<Integer> borrowBook(@PathVariable("bookId") Integer bookId,  Authentication authenticatedPatron) {
        return ResponseEntity.ok(borrowingService.borrowBook(bookId, authenticatedPatron));
    }

    @PostMapping("/return/{bookId}")
    public ResponseEntity<Integer> returnBook(@PathVariable("bookId") Integer bookId,  Authentication authenticatedPatron) {
        return ResponseEntity.ok(borrowingService.returnBook(bookId, authenticatedPatron));
    }
    

    

}
