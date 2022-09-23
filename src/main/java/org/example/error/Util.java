package org.example.error;

import java.util.function.Supplier;

/*
This contains a number of utility functions for working with the Result class.
All oif these functions are static, so you can call them directly from the class.
 */
public class Util {
    // This function repeatedly calls the given function until it returns an Ok. Useful for getting user input.
    // This causes an infinite loop if the given function doesn't cause side effects that would eventually cause it to return an Ok.
    public static <T> T LoopUntilOk(Supplier<Result<T>> f, String errMsg) {
        Result<T> result = f.get();
        while (result instanceof Err) {
            if (errMsg != null) {
                System.out.println(errMsg);
            }
            result = f.get();
        }
        try {
            return result.unwrap();
        } catch (Exception e) {
            throw new RuntimeException("This should never happen. Please report this bug.");
        }
    }
}
