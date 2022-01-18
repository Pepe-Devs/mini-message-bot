package com.pepedevs.minimessagebot;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        TextComponent textComponent = TextComponent.from(Converter.fromMini("<red>bruh<yellow><strikethrough>idk<italic>ok<reset><bold>ok"));
        try {
            ImageComponent image = new ImageComponent("img.png", textComponent);
            image.create(new File("output.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
