package org.example.error;

import java.util.function.Function;

public class Err<T> implements Result<T> {
    private final Exception exception;

    public Err(Exception exception) {
        this.exception = exception;
    }

    @Override
    public boolean isOk() {
        return false;
    }

    @Override
    public boolean isErr() {
        return true;
    }

    @Override
    public T unwrap() throws UnwrapOnErrException {
        throw new UnwrapOnErrException();
    }

    @Override
    public Exception unwrapErr() {
        return exception;
    }

    @Override
    public <U> Result<U> flatMap(Function<T, Result<U>> f) {
        // Since this is an Err, we just return another Err
        return new Err<>(exception);
    }
}
