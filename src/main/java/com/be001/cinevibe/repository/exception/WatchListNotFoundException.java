package com.be001.cinevibe.repository.exception;

public class WatchListNotFoundException extends RuntimeException {

    public WatchListNotFoundException(String message) {
        super(message);
    }
}
