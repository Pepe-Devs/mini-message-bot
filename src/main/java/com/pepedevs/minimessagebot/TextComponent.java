package com.pepedevs.minimessagebot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class TextComponent {

    private static transient final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @SerializedName("color")
    private String color;

    @SerializedName("extra")
    private List<TextComponent> extra = new ArrayList<>();

    @SerializedName("text")
    private String text;

    private boolean bold;
    private boolean italic;
    private boolean strikethrough;
    private boolean obfuscated;
    private boolean underlined;

    public static TextComponent from(String jsonObject) {
        return GSON.fromJson(jsonObject, TextComponent.class);
    }

    private TextComponent() {}

    public Color getColor() {
        if (this.color == null)
            return null;
        return Utils.getColor(this.color);
    }

    public List<TextComponent> getExtra() {
        return this.extra;
    }

    public String getText() {
        return this.text;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public boolean isStrikethrough() {
        return strikethrough;
    }

    public boolean isObfuscated() {
        return obfuscated;
    }

    public boolean isUnderlined() {
        return underlined;
    }

    public EnumSet<Modifiers> getModifiers() {
        EnumSet<Modifiers> set = EnumSet.noneOf(Modifiers.class);
        if (this.bold) set.add(Modifiers.BOLD);
        if (this.italic) set.add(Modifiers.ITALIC);
        if (this.strikethrough) set.add(Modifiers.STRIKETHROUGH);
        if (this.obfuscated) set.add(Modifiers.OBFUSCATED);
        if (this.underlined) set.add(Modifiers.UNDERLINED);
        return set;
    }

    public enum Modifiers {
        BOLD,
        ITALIC,
        STRIKETHROUGH,
        OBFUSCATED,
        UNDERLINED;
    }
}
