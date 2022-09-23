package org.example.error;

/*
The purpose of option is to describe exceptions in a programatic way without using throw or catch.
This is useful if we want to match against the possibility of an error inline, rather than after the fact.
 */
public interface Result<T> {
    // Result is an interface for two classes: Ok and Err
    // Ok-like classes have a value, Err-like classes have an exception
    // The default implementation of Ok-like classes is Ok
    // and the default implementation of Err-like classes is Err

    // Return true if the result is Ok-like, false otherwise
    public boolean isOk();

    // Return true if the result is Err-like, false otherwise
    public boolean isErr();

    // Return the value of the result if it is Ok-like
    // Throw an exception if the result is Err-like
    public T unwrap() throws UnwrapOnErrException;
}
