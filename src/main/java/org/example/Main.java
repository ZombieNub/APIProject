package org.example;

import org.example.ircbot.Bot;

public class Main {
    public static void main(String[] args) {
        Bot bot = new Bot();
        bot.setVerbose(true);
        try {
            bot.connect("irc.us.libera.chat");
            bot.joinChannel("#CS2336");
            bot.sendMessage("#CS2336", "Hello, I am a weather API bot! Available commands are !weather and !exit.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //org.example.weather.WeatherAPI.main();
    }
}