package com.bunny.eschataddons.HUD;

import com.bunny.eschataddons.GUI.GuiMaskTimers;
import com.bunny.eschataddons.config.ConfigHandler;
import com.bunny.eschataddons.features.MaskTimers;
import com.bunny.eschataddons.features.ScoreCalc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.DecimalFormat;

public class MaskTimersHUD {
    DecimalFormat df = new DecimalFormat("#.##");

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            this.drawHUD(event.resolution);
        }
    }

    private void drawHUD(ScaledResolution resolution) {
        if (!ConfigHandler.MaskTimersEnabled) return;
        if (MaskTimers.timeLeft <= 0 && !GuiMaskTimers.editingMaskTimer) return;
        String timeMessage = df.format(MaskTimers.timeLeft);
        final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        fr.drawStringWithShadow(timeMessage, ConfigHandler.MaskTimersHUD_X, ConfigHandler.MaskTimersHUD_Y, 0x00FF00);
    }
}