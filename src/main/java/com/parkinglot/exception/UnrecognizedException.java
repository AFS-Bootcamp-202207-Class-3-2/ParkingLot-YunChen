package com.parkinglot.exception;

public class UnrecognizedException extends Exception {
    public UnrecognizedException() {
        super("Unrecognized parking ticket");
    }
}
