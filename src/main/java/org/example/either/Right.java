package org.example.either;

import java.util.function.Function;

public class Right<L, R> implements Either<L, R> {
    public final R value;

    public Right(R value) {
        this.value = value;
    }

    public boolean isLeft() {
        return false;
    }

    public boolean isRight() {
        return true;
    }

    public L unwrapLeft() throws UnwrapLeftOnRightException {
        throw new UnwrapLeftOnRightException();
    }

    public R unwrapRight() {
        return this.value;
    }

    public <A, B> Either<A, B> bimap(Function<L, A> f, Function<R, B> g) {
        return new Right<>(g.apply(this.value));
    }

    public <U> Either<L, U> flatMapRight(Function<R, Either<L, U>> f) {
        return f.apply(this.value);
    }

    public <U> Either<U, R> flatMapLeft(Function<L, Either<U, R>> f) {
        return new Right<>(this.value);
    }

    public <U> U flatten(Function<L, U> f, Function<R, U> g) {
        return g.apply(this.value);
    }
}
