package org.example.either;

import java.util.function.Function;

/*
The purpose of either is to describe values which can be of A type or B type.
This is necessary if a function should be capable of returning more than one kind of value, such as a city name or zip code.
 */
public interface Either<L, R> {
    // Either is an interface for two classes: Left and Right
    // Both Left and Right have values. Left is of type L and R is of type R
    // L and R can be of the same type
    // The default implementation of Left is Left, and the default implementation of Right is Right
    // However, there can be Left-like classes and Right-like classes
    // A possible example would be Both, which is both Left-like and Right-like
    // However, this is not implemented in this library

    // Return true if the either is Left-like, false otherwise
    boolean isLeft();

    // Return true if the either is Right-like, false otherwise
    boolean isRight();

    // Return the value of the Left-like either. Throw an exception if the either is Right-like
    L unwrapLeft() throws UnwrapLeftOnRightException;

    // Return the value of the Right-like either. Throw an exception if the either is Left-like
    R unwrapRight() throws UnwrapRightOnLeftException;

    // Applies two functions to the value of the either, depending on whether it is Left-like or Right-like
    <A, B> Either<A, B> bimap(Function<L, A> f, Function<R, B> g);

    // Like all monads, Either needs a flatMap function
    // Due to the nature of Either, this function is a bit more complicated than the flatMap function for Result
    // This is because we can either flatmap Left-like objects or Right-like objects
    // The default implementation is to flatmap Right-like objects
    <U> Either<L, U> flatMapRight(Function<R, Either<L, U>> f);

    // This function is the same as flatMapRight, except it flatmaps Left-like objects
    <U> Either<U, R> flatMapLeft(Function<L, Either<U, R>> f);

    // Flattens the Either to a single value using two functions, depending on whether it is Left-like or Right-like
    <U> U flatten(Function<L, U> f, Function<R, U> g);
}
