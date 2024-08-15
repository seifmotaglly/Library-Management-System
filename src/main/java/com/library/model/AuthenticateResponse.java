package com.library.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticateResponse {

    private String token;
}
