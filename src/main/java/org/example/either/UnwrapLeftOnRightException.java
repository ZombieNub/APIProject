package org.example.either;

public class UnwrapLeftOnRightException extends Exception {
    public UnwrapLeftOnRightException() {
        super("Attempted to unwrap left on right");
    }
}