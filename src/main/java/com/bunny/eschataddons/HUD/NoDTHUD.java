package com.bunny.eschataddons.HUD;

import com.bunny.eschataddons.config.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoDTHUD {

    private int DTEnabled = 0xFF0000;  // Red when disabled
    private int DTDisabled = 0x00FF00; // Green when enabled

    private int CurrentColor;
    private String Downtime;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (!ConfigHandler.NoDTHUD) return;

        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            this.drawHUD(event.resolution);
        }
    }

    private void drawHUD(ScaledResolution resolution) {
        final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

        // Set text and color based on NoDT status
        if (ConfigHandler.NoDTEnabled) {
            Downtime = "NoDT";
            this.CurrentColor = DTDisabled; // Green when enabled
        } else {
            Downtime = "  DT ";
            this.CurrentColor = DTEnabled; // Red when disabled
        }

        // Draw string at configured position
        fr.drawStringWithShadow(Downtime, ConfigHandler.NoDTHUD_X, ConfigHandler.NoDTHUD_Y, this.CurrentColor);
    }
}