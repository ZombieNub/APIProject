package org.example.option;

import java.util.function.Function;

public class Some<T> implements Option<T> {
    // The default implementation of Some-like classes is Some
    // New Some-like classes can be created by extending Some, or by creating a new class that implements Option

    // The value of the option
    private T value;

    public Some(T value) {
        this.value = value;
    }

    public boolean isSome() {
        return true;
    }

    public boolean isNone() {
        return false;
    }

    public T get() throws InvalidGetOnOptionException {
        return value;
    }

    public <U> Option<U> map(Function<T, U> f) {
        return new Some<>(f.apply(value));
    }
}
