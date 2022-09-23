package org.example.error;

public class UnwrapOnErrException extends Exception {
    public UnwrapOnErrException() {
        super("unwrap() called on an err-like result");
    }
}
