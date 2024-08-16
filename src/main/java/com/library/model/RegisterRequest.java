package com.library.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {

    //TODO: Add validation for patronUsername
    @NotEmpty(message = "Please enter a valid username")
    @NotBlank(message = "Please enter a valid username")
    private String patronUsername;
    @NotEmpty(message = "Please enter your first name")
    @NotBlank(message = "Please enter your first name")
    private String patronFirstName;
    @NotEmpty(message = "Please enter your last name")
    @NotBlank(message = "Please enter your last name")
    private String patronLastName;
    @NotEmpty(message = "Please enter a valid email")
    @NotBlank(message = "Please enter a valid email")
    @Email(message = "Please enter a valid email")
    private String patronEmail;
    @NotEmpty(message = "Please enter a valid password")
    @NotBlank(message = "Please enter a valid password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String patronPassword;

}
