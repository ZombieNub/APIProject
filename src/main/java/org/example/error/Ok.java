package org.example.error;

import java.util.function.Function;

public class Ok<T> implements Result<T> {
    private final T value;

    public Ok(T value) {
        this.value = value;
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public boolean isErr() {
        return false;
    }

    @Override
    public T unwrap() {
        return value;
    }

    @Override
    public Exception unwrapErr() throws UnwrapErrOnOkException {
        throw new UnwrapErrOnOkException();
    }

    @Override
    public <U> Result<U> flatMap(Function<T, Result<U>> f) {
        // Since this is an Ok, we just return the result of the function
        return f.apply(value);
    }
}
