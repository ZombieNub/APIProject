package org.example.ircbot;

import org.jibble.pircbot.PircBot;

public class Bot extends PircBot {
    public Bot() {
        this.setName("myWeatherBot");
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        if (message.equalsIgnoreCase("!weather")) {
            sendMessage(channel, "Weather API not yet implemented!");
        }
    }
}
