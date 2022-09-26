package org.example.weather;

import org.example.error.Result;
import java.net.URL;
import org.example.error.*;

/*
This class takes a zip code or city name and returns a URL for the OpenWeatherMap API.
A builder is useful so we don't have to build the URL using raw string interpolation. We can instead use this class to build the URL for us.
 */
public class WeatherRequestBuilder {
    static final String baseURL = "https://api.openweathermap.org/data/2.5/weather";
    // For security reasons, the API key is not stored in this file. It's stored in a separate file that is not in the repository.
    // If you want to run this code, you will need to create a file called properties.json in the src/main/resources directory.
    // Then, in that file, put the following:
    /*
    {
        "apiKey": "YOUR_API_KEY_HERE"
    }
     */
    static Result<String> apiKey = APIKeyHandler.GetAPIKey();

    // This function takes a zip code and returns a URL for the OpenWeatherMap API.
    public static Result<URL> getUrlFromZipCode(int zipCode) {
        return apiKey.flatMap((String key) -> Util.Try(() -> new URL(baseURL + "?zip=" + zipCode + ",us&appid=" + key)));
    }

    // This function takes a city name and returns a URL for the OpenWeatherMap API.
    public static Result<URL> getUrlFromCityName(String cityName) {
        return apiKey.flatMap((String key) -> Util.Try(() -> new URL(baseURL + "?q=" + cityName + "&appid=" + key)));
    }
}
