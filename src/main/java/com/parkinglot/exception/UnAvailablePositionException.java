package com.parkinglot.exception;


import com.parkinglot.util.Constant;

public class UnAvailablePositionException extends Exception{
    public UnAvailablePositionException() {
        super(Constant.UnAvailablePositionException);
    }
}
