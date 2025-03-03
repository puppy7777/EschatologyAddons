package com.bunny.eschatAddons.features;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import com.bunny.eschatAddons.config.ConfigHandler;

public class PartyCommands {

    public PartyCommands() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {

        if (ConfigHandler.PartyCommandsEnabled) {

            // Get plain chat message without color codes or hidden characters
            String rawMessage = event.message.getUnformattedText().trim();

            // Normalize spaces, remove prefixes, and "From " part
            String formattedMessage = rawMessage.replaceAll("\\s+", " ")
                    .replace("[MVP+]", "")
                    .replace("[MVP]", "")
                    .replace("[VIP]", "")
                    .replace("[VIP+]", "")
                    .replace("[MVP++]", "")
                    .replace("From ", "") // Remove "From "
                    .trim(); // Trim any leading/trailing spaces

            // System.out.println("Formatted Message: " + formattedMessage);

            // Regex to match the pattern for "!inv"
            if (formattedMessage.matches("^(\\w+): !inv$")) {
                // Extract username from formattedMessage
                String playerName = formattedMessage.split(" ")[0].replace(":", "");

                System.out.println("Sending Command: /p " + playerName);

                Minecraft.getMinecraft().thePlayer.sendChatMessage("/p " + playerName);
            }
        }
    }



}

