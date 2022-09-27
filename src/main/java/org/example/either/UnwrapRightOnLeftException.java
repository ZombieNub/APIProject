package org.example.either;

public class UnwrapRightOnLeftException extends Exception {
    public UnwrapRightOnLeftException() {
        super("Attempted to unwrap right on left");
    }
}