package org.example.ircbot;

import org.jibble.pircbot.PircBot;
import org.example.weather.WeatherAPI;

public class Bot extends PircBot {
    public Bot() {
        this.setName("myWeatherBot");
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        // if the message is "!weather", then respond with the proper syntax
        if (message.trim().equalsIgnoreCase("!weather")) {
            sendMessage(channel, "Syntax: !weather <city/zipcode>");
        }
        // If the message is "!weather <parameter>", then respond with the city/zipcode.
        else if (message.trim().startsWith("!weather")) {
            String parameter = message.trim().substring(9);
            sendMessage(channel, "You asked for the weather in " + parameter);
            String weatherResponse = WeatherAPI.getWeather(parameter);
            // Due to errors, we may have to send multiple messages. We can do this by splitting the string into an array of strings, and then sending each string in the array.'
            String[] weatherResponseArray = weatherResponse.split("\n");
            for (String s : weatherResponseArray) {
                sendMessage(channel, s);
            }
        }
        if (message.equalsIgnoreCase("!exit")) {
            disconnect();
            System.exit(0);
        }
    }
}
