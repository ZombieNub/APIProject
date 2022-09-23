package org.example.option;

import java.util.function.Function;

/*
The purpose of Option is to describe a value that may or may not exist
Nonexistent values are entirely possible in all languages, but are not handled well by many languages
Usually, nulls, -1, or other values are used to represent nonexistence
But if the programmer is not expecting a value to be null, -1, or some other value, then they will not handle it properly
Option forces the programmer (myself) to handle nonexistence properly
I only see Option being necessary in this project for the API calls, as they may not return the desired values
 */
public interface Option<T> {
    // Option is an interface for two classes: Some and None
    // Some-like classes have a value, None-like classes don't
    // The default implementation of Some-like classes is Some
    // and the default implementation of None-like classes is None

    // Return true if the option is Some-like, false otherwise
    boolean isSome();

    // Return true if the option is None-like, false otherwise
    boolean isNone();

    // Return the value of the option if it is Some-like
    // Throw an exception if the option is None-like
    T get() throws InvalidGetOnOptionException;

    // Maps the function f over a Some-like. If the option is None-like, then it returns a None<U>
    <U> Option<U> map(Function<T, U> f);
}
