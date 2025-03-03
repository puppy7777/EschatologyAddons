package com.bunny.eschatAddons.features;

import com.bunny.eschatAddons.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.StringUtils;

import java.util.regex.Pattern;

public class DungeonFloorCommands {
    String[] floors = {"one", "two", "three", "four", "five", "six", "seven"};

    public DungeonFloorCommands() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (!ConfigHandler.DungeonFloorCommandsEnabled) {
            return;
        }
        if (event.isCanceled()) return;
        String rawMessage = StringUtils.stripControlCodes(event.message.getUnformattedText());
        // System.out.println("Cleaned message: " + rawMessage);
        boolean command = Pattern.compile("Party >.*?: ![fFmM][1234567]?").matcher(rawMessage).find();
        if (command) {
            System.out.println("Trying to enter a dungeon!");
            String[] parts = rawMessage.split("!", 2);
            // if parts[1] is more than three characters, ignore
            if (parts[1].length() > 3) return;
            try {
                boolean isMasterMode = (Character.toLowerCase(parts[1].charAt(0)) == 'm');
                int floor = parts[1].charAt(1) - '1';
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/joindungeon " +
                        ((isMasterMode) ? "master_" : "") + "catacombs_floor_" + floors[floor]);
            } catch (Exception ignored) {}
        }
    }
}
