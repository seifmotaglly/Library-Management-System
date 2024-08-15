package com.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatronResponse {

    private Integer patronId;
    private String patronUsername;
    private String patronFirstName;
    private String patronLastName;
    private String patronEmail;
    private String patronPassword;
}
