package org.example.either;

import java.util.function.Function;

public class Left<L, R> implements Either<L, R> {
    public final L value;

    public Left(L value) {
        this.value = value;
    }

    public boolean isLeft() {
        return true;
    }

    public boolean isRight() {
        return false;
    }

    public L unwrapLeft() {
        return this.value;
    }

    public R unwrapRight() throws UnwrapRightOnLeftException {
        throw new UnwrapRightOnLeftException();
    }

    public <A, B> Either<A, B> bimap(Function<L, A> f, Function<R, B> g) {
        return new Left<>(f.apply(this.value));
    }

    public <U> Either<L, U> flatMapRight(Function<R, Either<L, U>> f) {
        return new Left<>(this.value);
    }

    public <U> Either<U, R> flatMapLeft(Function<L, Either<U, R>> f) {
        return f.apply(this.value);
    }

    public <U> U flatten(Function<L, U> f, Function<R, U> g) {
        return f.apply(this.value);
    }
}
