package com.pepedevs.minimessagebot;

import com.google.gson.JsonElement;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.transformation.TransformationRegistry;
import net.kyori.adventure.text.minimessage.transformation.TransformationType;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class Converter {

    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.builder().character('&').hexCharacter('#').build();
    private static final MiniMessage MINI = MiniMessage.builder()
            .transformations(TransformationRegistry.builder()
                    .add(TransformationType.COLOR)
                    .add(TransformationType.DECORATION)
                    .add(TransformationType.HOVER_EVENT)
                    .add(TransformationType.CLICK_EVENT)
                    .add(TransformationType.KEYBIND)
                    .add(TransformationType.TRANSLATABLE)
                    .add(TransformationType.INSERTION)
                    .add(TransformationType.FONT)
                    .add(TransformationType.GRADIENT)
                    .add(TransformationType.RAINBOW)
                    .build())
            .strict(false)
            .build();
    private static final GsonComponentSerializer GSON = GsonComponentSerializer.gson();

    public static String fromLegacy(String input) {
        return GSON.serialize(LEGACY.deserialize(input));
    }

    public static String fromMini(String input) {
        return GSON.serialize(MINI.deserialize(input));
    }

    public static String legacyToMini(String input) {
        return MINI.serialize(LEGACY.deserialize(input));
    }

    public static JsonElement fromMiniToTree(String input) {
        return GSON.serializeToTree(MINI.deserialize(input));
    }

}