package com.be001.cinevibe.exception;

public class WatchListNotFoundException extends RuntimeException {

    public WatchListNotFoundException(String message) {
        super(message);
    }
}
