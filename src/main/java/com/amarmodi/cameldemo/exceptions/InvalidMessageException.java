package com.amarmodi.cameldemo.exceptions;

public class InvalidMessageException extends RuntimeException {
    public InvalidMessageException(String s) {
        super(s);
    }
}
