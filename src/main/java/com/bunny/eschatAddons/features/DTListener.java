package com.bunny.eschatAddons.features;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import com.bunny.eschatAddons.config.ConfigHandler;



public class DTListener {

    public boolean DtCalled;

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {

        if (ConfigHandler.PartyCommandsEnabled) {

            // Get plain chat message without color codes or hidden characters
            String FormattedText = event.message.getFormattedText().trim();


            System.out.println("Formatted Message: " + FormattedText);

            // Regex to match the pattern for "!inv"
            if (FormattedText.contains("Party >" + "!dt")) {
                // Extract username from formattedMessage
                System.out.println("Downtime Called!");

                DtCalled = true;
                return;

            }
        }
    }
}