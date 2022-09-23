package org.example.option;

import java.util.function.Function;

public class None<T> implements Option<T> {
    // The default implementation of None-like classes is None
    // New None-like classes can be created by extending None, or by creating a new class that implements Option

    public boolean isSome() {
        return false;
    }

    public boolean isNone() {
        return true;
    }

    public T get() throws InvalidGetOnOptionException {
        throw new InvalidGetOnOptionException();
    }

    public <U> Option<U> map(Function<T, U> f) {
        return new None<>();
    }

}
