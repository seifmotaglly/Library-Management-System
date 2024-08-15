package com.library.handler;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {

    private Integer errorCode;
    private String errorMessage;
    private String errorDetails;
    private Set<String> validationErrors;
    private Map<String, String> errors;

}
