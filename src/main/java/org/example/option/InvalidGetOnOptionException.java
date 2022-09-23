package org.example.option;

public class InvalidGetOnOptionException extends Exception {
    public InvalidGetOnOptionException() {
        super("get() called on a none-like option");
    }
}
