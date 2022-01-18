package com.pepedevs.minimessagebot;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Utils {
    
    private static final Map<String, Color> colorMap = new ConcurrentHashMap<>();
    
    static {
        colorMap.put("dark_red", Color.decode("0xAA0000"));
        colorMap.put("red", Color.decode("0xFF5555"));
        colorMap.put("gold", Color.decode("0xFFAA00"));
        colorMap.put("yellow", Color.decode("0xFFFF55"));
        colorMap.put("dark_green", Color.decode("0x00AA00"));
        colorMap.put("green", Color.decode("0x55FF55"));
        colorMap.put("aqua", Color.decode("0x55FFFF"));
        colorMap.put("dark_aqua", Color.decode("0x00AAAA"));
        colorMap.put("dark_blue", Color.decode("0x0000AA"));
        colorMap.put("blue", Color.decode("0x5555FF"));
        colorMap.put("light_purple", Color.decode("0xFF55FF"));
        colorMap.put("dark_purple", Color.decode("0xAA00AA"));
        colorMap.put("gray", Color.decode("0xAAAAAA"));
        colorMap.put("dark_gray", Color.decode("0x555555"));
        colorMap.put("black", Color.decode("0x000000"));
        colorMap.put("white", Color.decode("0xFFFFFF"));
    }

    public static Color getColor(String color) {
        return colorMap.getOrDefault(color, Color.WHITE);
    }
    
}
