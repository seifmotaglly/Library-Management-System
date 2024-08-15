package com.library.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.model.PatronRequest;
import com.library.model.PatronResponse;
import com.library.service.PatronService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/patrons")
@RequiredArgsConstructor
public class PatronController {
    private final PatronService patronService;

    @GetMapping
    public ResponseEntity<List<PatronResponse>> findAllPatrons() {
        return ResponseEntity.ok(patronService.findAllPatrons());
    }

    @GetMapping("/{patronId}")
    public ResponseEntity<PatronResponse> findPatronById(@PathVariable("patronId") Integer patronId) {
        return ResponseEntity.ok(patronService.findPatronById(patronId));
    }

    @PatchMapping
    public ResponseEntity<PatronResponse> updatePatron(@Valid @RequestBody PatronRequest patronRequest) {
        return ResponseEntity.ok(patronService.updatePatron(patronRequest));
    }

    @DeleteMapping
    public ResponseEntity<Integer> deletePatron() {
        return ResponseEntity.ok(patronService.deletePatron());
    }
    
}