package com.bunny.eschatAddons.features;

import com.bunny.eschatAddons.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraft.util.EnumChatFormatting;

import com.bunny.eschatAddons.features.DTListener;
import org.lwjgl.Sys;
import tv.twitch.chat.Chat;

public class NoDTListener {

    private long executeAtTime = 0; // Time when the command should be executed
    private boolean DtCalled = true;
    private boolean commandQueued = false; // Flag to track if a command is scheduled

    public NoDTListener() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this); // Register tick event
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (!ConfigHandler.NoDTEnabled) return;
        if (event.isCanceled()) return;


        // Remove Minecraft color codes and hidden characters
        String unFormattedMessage = event.message.getUnformattedText().trim()
                .replace("[MVP+]", "")
                .replace("[MVP]", "")
                .replace("[VIP]", "")
                .replace("[VIP+]", "")
                .replace("[MVP++]", "")
                .replace("From ", "") // Remove "From "
                .trim(); // Trim any leading/trailing spaces // Normalize spaces

        // System.out.println("Unformatted Message: " + unFormattedMessage);

        if (!DtCalled && unFormattedMessage.contains("!dt" )){
            DtCalled = true;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "[EschatAddons] " + EnumChatFormatting.GOLD + "Downtime Called!"));
            return;
        }

        if (unFormattedMessage.contains("> EXTRA STATS <")) {

            if (DtCalled) {
                DtCalled = false;
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/pc Downtime Called!");
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Downtime Called.. Cancelling Requeue"));
                return;
            }

            System.out.println("Detected EXTRA STATS message! Command will be sent after delay...");
            executeAtTime = System.currentTimeMillis() + ConfigHandler.NoDTDelay;
            commandQueued = true; // Mark command for future execution
            if (commandQueued){
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Downtime NOT called, requeuing"));
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!commandQueued) return;

        if (System.currentTimeMillis() >= executeAtTime) {
            if (Minecraft.getMinecraft().thePlayer != null) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/instancerequeue");
                System.out.println("Sending /instancerequeue after delay!");
            }
            commandQueued = false; // Reset flag after execution
        }
    }
}
