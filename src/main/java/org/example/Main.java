package org.example;

import org.example.error.Err;
import org.example.error.Ok;
import org.example.error.Result;
import org.example.error.Util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static Result<Integer> GetZipCityChoice(Scanner in) {
        System.out.println("Would you like to search by zip code or city name?");
        System.out.println("1. Zip code");
        System.out.println("2. City name");
        System.out.print("Enter your choice: ");
        String input = in.nextLine().trim();
        if (input.equals("1")) {
            return new Ok<>(1);
        } else if (input.equals("2")) {
            return new Ok<>(2);
        } else {
            return new Err<>(new InputMismatchException("Invalid input. Expected 1 or 2."));
        }
    }

    // This function gets the zip code from the user.
    public static Result<Integer> GetZipCode(Scanner in) {
        System.out.print("Enter a zip code: ");
        String input = in.nextLine();
        try {
            int zipCode = Integer.parseInt(input.trim());
            return new Ok<>(zipCode);
        } catch (NumberFormatException e) {
            return new Err<>(e);
        }
    }

    // This function gets the city name from the user.
    public static String GetCityName(Scanner in) {
        System.out.print("Enter a city name: ");
        return in.nextLine().trim();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int choice = Util.LoopUntilOk(() -> GetZipCityChoice(in), "Invalid input. Expected 1 or 2.");
        switch (choice) {
            case 1 -> {
                int zipCode = Util.LoopUntilOk(() -> GetZipCode(in), "Invalid input. Expected a zip code.");
                System.out.println("You entered zip code " + zipCode);
            }
            case 2 -> {
                String cityName = GetCityName(in);
                System.out.println("You entered city name " + cityName);
            }
            default -> throw new RuntimeException("This should never happen. Please report this bug.");
        }
    }
}