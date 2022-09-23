package org.example.error;

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
}
