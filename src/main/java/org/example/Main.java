package org.example;
import org.example.option.*;
import java.util.Scanner;

public class Main {
    // This function gets the zip code from the user.
    // The expected input is a console scanner, though any scanner can be used.
    public static Option<Integer> GetZipCode(Scanner in) {
        System.out.print("Enter a zip code: ");
        String input = in.nextLine();
        try {
            return new Some<>(Integer.parseInt(input));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}