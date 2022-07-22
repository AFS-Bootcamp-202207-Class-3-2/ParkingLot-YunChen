package com.parkinglot.exception;


public class UnAvailablePositionException extends Exception{
    public UnAvailablePositionException() {
        super("No available position");
    }
}
