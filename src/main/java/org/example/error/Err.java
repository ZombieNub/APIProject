package org.example.error;

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
}
