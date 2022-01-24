package com.pepedevs.minimessagebot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ImageComponent {

    public static final int FONT_SIZE = 100;
    private static final float BACKGROUND_ALPHA = 0.4F;
    private static final float SHADOW_ALPHA = 0.6F;
    private static final int SHADOW_OFFSET = 5;

    private final BufferedImage background;

    private final TextComponent text;
    private final List<ImageAttribute> attributes;

    private final StringBuilder finalStrippedText;

    private int current = 0;

    public ImageComponent(String backgroundImage, TextComponent component) throws IOException {
        this.text = component;
        this.attributes = new ArrayList<>();
        this.background = ImageIO.read(new File("background/" + backgroundImage));
        this.finalStrippedText = new StringBuilder();
    }

    public void generateFile(File file) {
        loop(this.text, EnumSet.noneOf(TextComponent.Modifiers.class), Color.WHITE);
        write();
        try {
            ImageIO.write(this.background, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.background.flush();
        }
    }

    private void write() {
        Graphics2D graphics = this.background.createGraphics();
        AttributedString writedString = new AttributedString(this.finalStrippedText.toString());
        AttributedString shadowString = new AttributedString(this.finalStrippedText.toString());
        attributes.forEach(imageAttribute -> {
            if (imageAttribute.startingIndex >= imageAttribute.endingIndex) return;
            writedString.addAttribute(imageAttribute.attribute, imageAttribute.attributeVal, imageAttribute.startingIndex, imageAttribute.endingIndex);
            if (imageAttribute.attribute.equals(TextAttribute.FOREGROUND)) {
                Color color = (Color) imageAttribute.attributeVal;
                color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (SHADOW_ALPHA * 255));
                shadowString.addAttribute(imageAttribute.attribute, color, imageAttribute.startingIndex, imageAttribute.endingIndex);
            } else {
                shadowString.addAttribute(imageAttribute.attribute, imageAttribute.attributeVal, imageAttribute.startingIndex, imageAttribute.endingIndex);
            }
        });
        writedString.addAttribute(TextAttribute.BACKGROUND, new Color(1, 1, 1, (int) (BACKGROUND_ALPHA * 255)));
        graphics.drawString(writedString.getIterator(), 50, 500);
        graphics.drawString(shadowString.getIterator(), 50 + SHADOW_OFFSET, 500 + SHADOW_OFFSET);
        graphics.dispose();
    }

    private void loop(TextComponent component, EnumSet<TextComponent.Modifiers> inhertiedModifiers, Color inheritedColor) {
        EnumSet<TextComponent.Modifiers> modifiers = EnumSet.copyOf(inhertiedModifiers);
        modifiers.addAll(component.getModifiers());
        Color color = inheritedColor;
        if (component.getColor() != null) color = component.getColor();
        current = this.processPart(component, modifiers, color);
        for (TextComponent child : component.getExtra()) {
            loop(child, modifiers, color);
        }
    }

    private int processPart(TextComponent component, EnumSet<TextComponent.Modifiers> modifiers, Color defaultColor) {
        int end = this.current + component.getText().length();
        EnumSet<TextComponent.Modifiers> appliedModifiers = EnumSet.noneOf(TextComponent.Modifiers.class);
        if (!modifiers.isEmpty()) appliedModifiers.addAll(modifiers);
        if (!component.getModifiers().isEmpty()) appliedModifiers.addAll(component.getModifiers());
        this.attributes.add(new ImageAttribute(this.current, end, TextAttribute.FONT, this.getFont(appliedModifiers)));
        this.attributes.add(new ImageAttribute(this.current, end, TextAttribute.FOREGROUND, (component.getColor() == null ? defaultColor : component.getColor())));
        if(appliedModifiers.contains(TextComponent.Modifiers.STRIKETHROUGH)) this.attributes.add(new ImageAttribute(this.current, end, TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON));
        if(appliedModifiers.contains(TextComponent.Modifiers.UNDERLINED)) this.attributes.add(new ImageAttribute(this.current, end, TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON));
        this.finalStrippedText.append(component.getText());
        return end;
    }

    private Font getFont(EnumSet<TextComponent.Modifiers> modifiers) {
        if (modifiers.contains(TextComponent.Modifiers.BOLD) && modifiers.contains(TextComponent.Modifiers.ITALIC))
            return MinecraftFont.BOLD_ITALIC.deriveFont(Font.BOLD | Font.ITALIC, FONT_SIZE);
        else if (modifiers.contains(TextComponent.Modifiers.BOLD))
            return MinecraftFont.BOLD.deriveFont(Font.BOLD, FONT_SIZE);
        else if (modifiers.contains(TextComponent.Modifiers.ITALIC))
            return MinecraftFont.ITALIC.deriveFont(Font.ITALIC, FONT_SIZE);
        return MinecraftFont.REGULAR.deriveFont(Font.PLAIN, FONT_SIZE);
    }

    private record ImageAttribute(int startingIndex, int endingIndex, TextAttribute attribute, Object attributeVal) {}

}
