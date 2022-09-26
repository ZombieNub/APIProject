package org.example.error;

/*
Normal java functional interfaces are not allowed to throw exceptions.
This is a functional interface that allows exceptions to be thrown.
This is necessary for the Try function.
Since this is a variant of the Supplier functional interface, it is called ExceptionalSupplier.
And it's main function is get, which is the same as the Supplier functional interface.
This allows this to be used in place of a Supplier.
 */
public interface ExceptionalSupplier<T> {
    T get() throws Exception;
}