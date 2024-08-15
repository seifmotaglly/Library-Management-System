package com.library.model;

// TODO: null handling and validation
public record PatronRequest(
        Integer patronId,
        String patronUsername,
        String patronFirstName,
        String patronLastName,
        String patronEmail,
        String patronPassword) {

}