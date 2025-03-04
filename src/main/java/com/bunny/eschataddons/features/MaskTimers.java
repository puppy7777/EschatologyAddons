package com.bunny.eschataddons.features;

import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.regex.Pattern;

public class MaskTimers {
    public static double timeLeft = 0;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String formattedMessage = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (Pattern.compile("^Second Wind Activated! Your Spirit Mask saved your life!$").matcher(formattedMessage).find()) {
            timeLeft = 3;
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/pc Spirit Mask Procced");
        }
        else if (Pattern.compile("^Your (⚚ )?Bonzo's Mask saved your life!$").matcher(formattedMessage).find()) {
            timeLeft = 3;
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/pc Bonzo Mask Procced");
        }
        else if (Pattern.compile("^Your Phoenix Pet saved you from certain death!$").matcher(formattedMessage).find()) {
            timeLeft = 4;
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/pc Phoenix Pet Procced");
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent tick) {
        if (tick.phase != TickEvent.Phase.END) return;
        if (timeLeft > 0) {
            timeLeft = timeLeft - 0.05;
        } else {
            timeLeft = 0;
        }
    }

}
