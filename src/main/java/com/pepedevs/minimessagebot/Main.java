package com.pepedevs.minimessagebot;

public class Main {

    public static void main(String[] args) {

        String token = null;
        String guildID = null;

        for (String arg : args) {
            if (arg.startsWith("-token:")) token = arg.substring(7);
            if (arg.startsWith("-guildID:")) guildID = arg.substring(9);
        }

        if (token == null || guildID == null) {
            throw new IllegalArgumentException("No token or guildID arguments!");
        }

        new DiscordHook(token).init(guildID);

        System.out.println("BOT STARTED!!!");
    }

}
