package com.github.adrian83.mordeczki.auth.model.payload;



public enum TokenType {
    BEARER("Bearer");

    private final String label;

    private TokenType(String label) {
        this.label = label;
    }

    public String label(){
        return label; 
    }
}