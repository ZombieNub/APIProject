package org.example.error;

public class UnwrapErrOnOkException extends Exception {
    public UnwrapErrOnOkException() {
        super("unwrapErr() called on an ok-like result");
    }
}
