package com.pepedevs.minimessagebot;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MinecraftFont {

    public static final Font REGULAR = define("regular.ttf");
    public static final Font BOLD = define("bold.ttf");
    public static final Font ITALIC = define("italic.ttf");
    public static final Font BOLD_ITALIC = define("bold_italic.ttf");

    public static Font define(String fontFile) {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/" + fontFile));
            ge.registerFont(font);
            return font;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

}
