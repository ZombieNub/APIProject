package org.example.weather;

import com.google.gson.JsonObject;
import org.example.either.Either;
import org.example.either.Left;
import org.example.either.Right;
import org.example.error.Err;
import org.example.error.Ok;
import org.example.error.Result;
import org.example.error.Util;

import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WeatherAPI {
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

    public static double ToFahrenheit(double kelvin) {
        return ((kelvin - 273.15) * (9.0 / 5.0)) + 32;
    }

    public static void main() {
        Scanner in = new Scanner(System.in);
        // It's not necessary to make zipCode and cityNames Result objects, but it's a good idea to do so anyway.
        // This is because they could possibly fail to be created, and if they do, we want to handle that error.
        // So by default, they are Err objects. If they are successfully created, we can change them to Ok objects.
        // It's impossible for them to be anything but Err objects, but who knows what may happen.
        Either<Result<Integer>, Result<String>> zipCodeOrCityName;
        int choice = Util.LoopUntilOk(() -> GetZipCityChoice(in), "Invalid input. Expected 1 or 2.");
        switch (choice) {
            case 1 -> zipCodeOrCityName = new Left<>(new Ok<>(Util.LoopUntilOk(() -> GetZipCode(in), "Invalid input. Expected a zip code.")));
            case 2 -> zipCodeOrCityName = new Right<>(new Ok<>(GetCityName(in)));
            default -> throw new RuntimeException("This should never happen. Please report this bug.");
        }

        // Now that we have the zip code or city name, we can use it to get the weather.
        // First we need to construct the API call.
        Result<URL> urlResult = zipCodeOrCityName.flatten(
                (Result<Integer> zipCode) -> zipCode.flatMap(WeatherRequestBuilder::getUrlFromZipCode),
                (Result<String> cityName) -> cityName.flatMap(WeatherRequestBuilder::getUrlFromCityName)
        );

        // Now we can use the URL to get the weather.
        Result<HttpURLConnection> connection = urlResult.flatMap(
                url -> Util.Try(
                        () -> (HttpURLConnection) url.openConnection()
                )
        );

        Result<Integer> status = connection.flatMap(
                conn -> Util.Try(
                        () -> {
                                conn.setRequestMethod("GET");
                                conn.setConnectTimeout(5000);
                                conn.setReadTimeout(5000);
                                return conn.getResponseCode();
                        }
                )
        );

        // Finally, we can get the weather's JSON.
        Result<Reader> inStream;
        inStream = status.flatMap(
                stat -> {
                    if (stat < 300) {
                        return connection.flatMap(
                                conn -> Util.Try(
                                        () -> new java.io.InputStreamReader(conn.getInputStream())
                                )
                        );
                    } else {
                        return connection.flatMap(
                                conn -> Util.Try(
                                        () -> new java.io.InputStreamReader(conn.getErrorStream())
                                )
                        );
                    }
                }
        );

        // Now that we have the JSON, we can parse it.
        Result<JsonObject> json = inStream.flatMap(
                inStr -> Util.Try(
                        () -> com.google.gson.JsonParser.parseReader(inStr).getAsJsonObject()
                )
        );

        // Now we can display the results to the user.
        // We have two options: Either print the JSON data directly, or compile it into a string and print that.
        // We will do the latter, especially since we can have the last function finally handle the error, if one occurs.

        Result<String> printStr = json.flatMap(
                rJson -> {
                    // We need to figure out what to do based on the status code.
                    // If the status code is >= 300, then we need to print the error stream.
                    // Otherwise, we need to print elements from the JSON in a pretty format.

                    // Since status is currently wrapped in a Result, we need to flatMap it.
                    return status.flatMap(
                            statusCode -> {
                                if (statusCode >= 300) {
                                    // This indicates that an error occurred in the request.
                                    // So we need to return the error message
                                    return Util.Try(
                                            () -> rJson.get("message").getAsString()
                                    );
                                } else {
                                    // This indicates that the request was successful, so we need to return the weather data.
                                    // Pretty formatting is best, so lets try being pretty.
                                    // We will print the:
                                    /*
                                        - City name
                                        - Temperature (Max, Min, Current)
                                        - Feel like temperature
                                        - Humidity
                                        - Wind speed
                                        - Wind direction
                                        - Weather description
                                     */
                                    return Util.Try(
                                            () -> {
                                                String jCityName = rJson.get("name").getAsString();
                                                JsonObject main = rJson.get("main").getAsJsonObject();
                                                // Each of the following are in Kelvin.
                                                // We need to convert them to Fahrenheit.
                                                double temp = ToFahrenheit(main.get("temp").getAsDouble());
                                                double tempMin = ToFahrenheit(main.get("temp_min").getAsDouble());
                                                double tempMax = ToFahrenheit(main.get("temp_max").getAsDouble());
                                                double feelsLike = ToFahrenheit(main.get("feels_like").getAsDouble());
                                                double humidity = main.get("humidity").getAsDouble();
                                                JsonObject wind = rJson.get("wind").getAsJsonObject();
                                                double windSpeed = wind.get("speed").getAsDouble();
                                                double windDeg = wind.get("deg").getAsDouble();
                                                String weatherDesc = rJson.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();
                                                return String.format(
                                                        "City name: %s%n" +
                                                        "Temperature: %f%n" +
                                                        "Temperature (Min): %f%n" +
                                                        "Temperature (Max): %f%n" +
                                                        "Feels like: %f%n" +
                                                        "Humidity: %f%n" +
                                                        "Wind speed: %f%n" +
                                                        "Wind direction: %f%n" +
                                                        "Weather description: %s%n",
                                                        jCityName, temp, tempMin, tempMax, feelsLike, humidity, windSpeed, windDeg, weatherDesc
                                                );
                                            }
                                    );
                                }
                            }
                    );
                }
        );

        // Now we can print the string.
        // If there is an error, we will print the error message.
        try {
            System.out.println(printStr.unwrap());
        } catch (Exception e) {
            try {
                System.out.println("Something went wrong: " + printStr.unwrapErr());
            } catch (Exception ex) {
                System.out.println("Something went wrong: " + ex.getMessage());
            }
        }
    }
}