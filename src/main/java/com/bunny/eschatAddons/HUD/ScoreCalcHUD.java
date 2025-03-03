package com.bunny.eschatAddons.HUD;

import com.bunny.eschatAddons.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.bunny.eschatAddons.features.ScoreCalc;

public class ScoreCalcHUD {

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            this.drawHUD(event.resolution);
        }
    }

    private void drawHUD(ScaledResolution resolution) {
        ScoreCalc.calcScore();
        final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        String scoreMessage = "Score: " + ScoreCalc.dungeonScore[0];
        String secretsNeeded = "Secrets to S+: " + ScoreCalc.dungeonScore[1];
        int scoreColor = (ScoreCalc.dungeonScore[0] >= 300) ? 0x00FF00 : 0xFF0000;
        int secretsColor = (ScoreCalc.dungeonScore[1] != 0) ? 0xFF0000 : 0x00FF00;
        fr.drawStringWithShadow(scoreMessage, ConfigHandler.ScoreCalcHUD_X, ConfigHandler.ScoreCalcHUD_Y, scoreColor);
        fr.drawStringWithShadow(secretsNeeded, ConfigHandler.ScoreCalcHUD_X, ConfigHandler.ScoreCalcHUD_Y + 10, secretsColor);
    }
}