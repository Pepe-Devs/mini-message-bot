package com.pepedevs.minimessagebot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.AttributedString;
import java.util.EnumSet;

public class ImageComponent {

    public static final int FONT_SIZE = 200;

    private final BufferedImage image;
    private final TextComponent text;

    private int length = 0;

    public ImageComponent(String fileName, TextComponent text) throws IOException {
        this.image = ImageIO.read(new File("background/" + fileName));
        this.text = text;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void create(File file) {
        this.iterate(this.text, this.text.getModifiers(), Color.WHITE, 0);
        try {
            ImageIO.write(this.image, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.image.flush();
        }
    }

    private void iterate(TextComponent component, EnumSet<TextComponent.Modifiers> modifiers, Color color, int x) {
        Color clr = color;
        EnumSet<TextComponent.Modifiers> mdfiers = EnumSet.copyOf(modifiers);
        Font font = this.getFont(modifiers);
        if (component.getColor() != null)
            clr = component.getColor();
        if (!component.getModifiers().isEmpty())
            mdfiers.addAll(component.getModifiers());
        double width = this.write(font, clr, component, x);
        this.length += width;

        this.length += FONT_SIZE / 10;

        for (TextComponent textComponent : component.getExtra()) {
            this.iterate(textComponent, mdfiers, clr, length);
        }
    }

    private double write(Font font, Color color, TextComponent component, int x) {
        if (!component.getText().isEmpty()) {
            Graphics g = this.image.getGraphics();

            AttributedString string = new AttributedString(component.getText());
            string.addAttribute(TextAttribute.FONT, font);
            string.addAttribute(TextAttribute.FOREGROUND, color);
            if (component.isStrikethrough())
                string.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
            if (component.isUnderlined())
                string.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

            FontMetrics ruler = g.getFontMetrics(font);
            GlyphVector vector = font.createGlyphVector(ruler.getFontRenderContext(), string.getIterator());
            Shape outline = vector.getOutline(0, 0);
            double expectedWidth = outline.getBounds().getWidth();

            g.drawString(string.getIterator(), x, 500);
            g.dispose();

            return expectedWidth;
        }

        return 0;
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

}
