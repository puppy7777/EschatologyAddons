package com.bunny.eschataddons.HUD;

import com.bunny.eschataddons.GUI.GuiScoreCalc;
import com.bunny.eschataddons.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.bunny.eschataddons.features.ScoreCalc;

public class ScoreCalcHUD {

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            this.drawHUD(event.resolution);
        }
    }

    private void drawHUD(ScaledResolution resolution) {
        if (!ConfigHandler.ScoreCalcEnabled) return;
        ScoreCalc.updateScore();
        if (!GuiScoreCalc.editingScoreCalc && (ScoreCalc.dungeonScore[0] == -1 || ScoreCalc.dungeonScore[1] == -1)) return;
        final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        String scoreMessage = "Score: " + ScoreCalc.dungeonScore[0] + " (" + ScoreCalc.dungeonScore[1] + ")";
        int scoreColor = (ScoreCalc.dungeonScore[0] >= 300) ? 0x00FF00 : 0xFF0000;
        fr.drawStringWithShadow(scoreMessage, ConfigHandler.ScoreCalcHUD_X, ConfigHandler.ScoreCalcHUD_Y, scoreColor);
    }
}