package org.example.weather;

import org.example.error.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/*
For security reasons, the API key is not stored in the repository.
This means we also cannot store it directly in the code.
So we need to put the API key in a separate file, and then read it from there.
This class handles that.
 */
public class APIKeyHandler {
    static boolean hasReadKey = false;
    static String apiKey = "";
    public static Result<String> GetAPIKey() {
        // If the API key already exists, return it.
        if (hasReadKey) {
            return new Ok<>(apiKey);
        }
        // Otherwise, read the API key from the file.
        else {
            // First we need to get the file itself
            try (
                InputStream inputStream = APIKeyHandler.class.getClassLoader().getResourceAsStream("properties.json")
            ) {
                // If we are in this block, then the file exists. We can now read it.
                if (inputStream == null) {
                    return new Err<>(new IOException("Could not find properties.json"));
                }
                String jString = new String(inputStream.readAllBytes());
                JSONObject jObject = new JSONObject(jString);
                apiKey = jObject.getString("apiKey");
                hasReadKey = true;
                return new Ok<>(apiKey);
            } catch (Exception e) {
                return new Err<>(e);
            }
        }
    }
}
