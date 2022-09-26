package org.example.error;

import java.util.function.Function;

/*
The purpose of option is to describe exceptions in a programmatic way without using throw or catch.
This is useful if we want to match against the possibility of an error inline, rather than after the fact.
 */
public interface Result<T> {
    // Result is an interface for two classes: Ok and Err
    // Ok-like classes have a value, Err-like classes have an exception
    // The default implementation of Ok-like classes is Ok
    // and the default implementation of Err-like classes is Err

    // Return true if the result is Ok-like, false otherwise
    boolean isOk();

    // Return true if the result is Err-like, false otherwise
    boolean isErr();

    // Return the value of the result if it is Ok-like
    // Throw an exception if the result is Err-like
    T unwrap() throws UnwrapOnErrException;

    // Return the exception of the result if it is Err-like
    // Throw an exception if the result is Ok-like
    Exception unwrapErr() throws UnwrapErrOnOkException;

    // Like all monads, Result needs a flatMap function
    // This makes composing Result objects easier
    // Composing Ok-like objects feeds the result of the value into the given function
    // Composing Err-like objects returns the Err-like object
    <U> Result<U> flatMap(Function<T, Result<U>> f);
}
