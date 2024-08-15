package com.library.model;

import lombok.Getter;

@Getter
public enum EmailTemplate {


    ACTIVATION("activation");

    private final String name;
    
    EmailTemplate(String name) {
        this.name = name;
    }
}
