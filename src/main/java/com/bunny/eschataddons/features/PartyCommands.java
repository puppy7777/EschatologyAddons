package com.bunny.eschataddons.features;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import com.bunny.eschataddons.config.ConfigHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.regex.Pattern;

public class PartyCommands {
    String[] floors = {"one", "two", "three", "four", "five", "six", "seven"};
    boolean DtCalled = false;
    long executeAtTime = 0;
    boolean commandQueued = false;


    public PartyCommands() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {

        String formattedMessage = StringUtils.stripControlCodes(event.message.getUnformattedText());
        // System.out.println("Formatted Message: " + formattedMessage);

        // Regex to match the pattern for "!inv"
        //!invite
        if (Pattern.compile("^From [^:]*?: !inv(ite)?$" ).matcher(formattedMessage).find() && ConfigHandler.PartyInvEnabled) {

            // Extract username from formattedMessage
            String playerName = formattedMessage.split(" ")[0].replace(":", "");

            System.out.println("Sending Command: /p " + playerName);

            Minecraft.getMinecraft().thePlayer.sendChatMessage("/p " + playerName);
        }
        //!f7
        else if (Pattern.compile("^Party >[^:]*?: ![fFmM][1234567]$").matcher(formattedMessage).find() && ConfigHandler.PartyCommandsEnabled) {
            System.out.println("Trying to enter a dungeon!");
            String[] parts = formattedMessage.split("!", 2);
            // if parts[1] is more than three characters, ignore
            if (parts[1].length() > 3) return;
            try {
                boolean isMasterMode = (Character.toLowerCase(parts[1].charAt(0)) == 'm');
                int floor = parts[1].charAt(1) - '1';
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/joindungeon " +
                        ((isMasterMode) ? "master_" : "") + "catacombs_floor_" + floors[floor]);
            } catch (Exception ignored) {}
        }
        //allinv
        else if (Pattern.compile("^Party >[^:]*?: !allinv(ite)?$").matcher(formattedMessage).find() && ConfigHandler.PartyCommandsEnabled) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/p settings allinvite true");
        }
        //!dt
        else if (Pattern.compile("^Party >[^:]*?: !dt$").matcher(formattedMessage).find() && ConfigHandler.NoDTEnabled){
            DtCalled = true;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "[EschatAddons] " + EnumChatFormatting.GOLD + "Downtime Called!"));
            return;
        }
        //!dt checking
        else if (formattedMessage.contains("> EXTRA STATS <") && ConfigHandler.NoDTEnabled) {
            if (DtCalled) {
                DtCalled = false;
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/pc Downtime Called!");
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Downtime Called.. Cancelling Requeue"));
                return;
            }
            System.out.println("Detected EXTRA STATS message! Command will be sent after delay...");
            executeAtTime = System.currentTimeMillis() + ConfigHandler.NoDTDelay;
            commandQueued = true; // Mark command for future execution
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Downtime NOT called, requeuing"));
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

