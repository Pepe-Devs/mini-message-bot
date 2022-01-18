package com.pepedevs.minimessagebot;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Utils {
    
    private static final Map<String, Color> COLOR_MAP = new ConcurrentHashMap<>();
    
    static {
        COLOR_MAP.put("dark_red", Color.decode("0xAA0000"));
        COLOR_MAP.put("red", Color.decode("0xFF5555"));
        COLOR_MAP.put("gold", Color.decode("0xFFAA00"));
        COLOR_MAP.put("yellow", Color.decode("0xFFFF55"));
        COLOR_MAP.put("dark_green", Color.decode("0x00AA00"));
        COLOR_MAP.put("green", Color.decode("0x55FF55"));
        COLOR_MAP.put("aqua", Color.decode("0x55FFFF"));
        COLOR_MAP.put("dark_aqua", Color.decode("0x00AAAA"));
        COLOR_MAP.put("dark_blue", Color.decode("0x0000AA"));
        COLOR_MAP.put("blue", Color.decode("0x5555FF"));
        COLOR_MAP.put("light_purple", Color.decode("0xFF55FF"));
        COLOR_MAP.put("dark_purple", Color.decode("0xAA00AA"));
        COLOR_MAP.put("gray", Color.decode("0xAAAAAA"));
        COLOR_MAP.put("dark_gray", Color.decode("0x555555"));
        COLOR_MAP.put("black", Color.decode("0x000000"));
        COLOR_MAP.put("white", Color.decode("0xFFFFFF"));
    }

    public static Color getColor(String color) {
        return COLOR_MAP.get(color);
    }
    
}
