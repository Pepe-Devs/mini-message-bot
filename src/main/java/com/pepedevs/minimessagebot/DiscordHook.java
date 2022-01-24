package com.pepedevs.minimessagebot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.UUID;

public class DiscordHook extends ListenerAdapter {

    private JDA jda;

    private final CommandData convertCommand;
    private final CommandData viewCommand;

    public DiscordHook(String token) {
        try {
            this.jda = JDABuilder.createDefault(token)
                    .addEventListeners(this)
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        try {
            this.jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.convertCommand =
                new CommandData("convert", "use this command to convert legacy message format to minimessage format")
                        .addOption(OptionType.STRING, "text", "the text to be converted", true);

        this.viewCommand =
                new CommandData("view", "use this command to get an approximate look of the text")
                        .addOption(OptionType.STRING, "text", "Text you want to be displayed", true);
    }

    public void init(String guildID) {
        Guild guild = this.jda.getGuildById(guildID);
        if (guild == null) throw new IllegalArgumentException("Invalid Guild ID - " + guildID);
        guild.upsertCommand(convertCommand).queue();
        guild.upsertCommand(viewCommand).queue();
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        InteractionHook hook = event.getHook();
        hook.setEphemeral(false);
        event.deferReply().queue();
        if (event.getName().equals(this.convertCommand.getName())) {
            hook.editOriginal(Converter.legacyToMini(event.getOption("text").getAsString())).queue();
        } else if (event.getName().equals(this.viewCommand.getName())) {
            File output = new File(UUID.randomUUID() + ".png");
            try {
                ImageComponent image = new ImageComponent("img.png",
                        TextComponent.from(Converter.fromMini(event.getOption("text").getAsString())));
                image.generateFile(output);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (output.exists()) {
                hook.editOriginal(output).queue(ignored -> output.delete(), ignored -> output.delete());
            }else {
                hook.editOriginal("Unable to process").queue();
            }
        }
    }
}
