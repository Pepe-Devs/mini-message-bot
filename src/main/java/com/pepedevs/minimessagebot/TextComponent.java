package com.pepedevs.minimessagebot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.awt.*;
import java.util.List;

public class TextComponent {

    private static transient final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @SerializedName("color")
    private String color;

    @SerializedName("extra")
    private List<TextComponent> extra;

    @SerializedName("text")
    private String text;

    private boolean bold;
    private boolean italic;
    private boolean strikethrough;
    private boolean obfuscated;
    private boolean underlined;

    public static TextComponent from(JsonObject jsonObject) {
        return GSON.fromJson(jsonObject, TextComponent.class);
    }

    private TextComponent() {}

    public Color getColor() {
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
}
