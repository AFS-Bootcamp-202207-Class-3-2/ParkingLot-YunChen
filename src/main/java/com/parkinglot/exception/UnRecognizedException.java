package com.parkinglot.exception;

import com.parkinglot.util.Constant;

public class UnRecognizedException extends Exception {
    public UnRecognizedException() {
        super(Constant.UnRecognizedTicketException);
    }
    public UnRecognizedException(String msg) {
        super(msg);
    }
}
