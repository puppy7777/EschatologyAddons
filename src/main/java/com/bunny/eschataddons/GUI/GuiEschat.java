package com.bunny.eschataddons.GUI;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiEschat extends GuiScreen {

    @Override
    public void initGui() {
        buttonList.clear();

        buttonList.add(new GuiButton(10, width / 2 - 75, height / 2 - 25, 150, 20, "NoDT Settings"));
        buttonList.add(new GuiButton(20, width / 2 - 75, height /2, 150, 20, "Party Commands Setting"));
        buttonList.add(new GuiButton(30, width / 2 - 75, height /2 + 25, 150, 20, "Dungeon Score Calc"));
        buttonList.add(new GuiButton(40, width / 2 - 75, height /2 + 50, 150, 20, "Mask Timers"));


    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 10) {
            mc.displayGuiScreen(new GuiNoDTSettings(this)); // Open NoDT Settings Page
        } else if (button.id == 20) {
            mc.displayGuiScreen(new GuiPartyCommands(this));
        } else if (button.id == 30) {
            mc.displayGuiScreen(new GuiScoreCalc(this));
        } else if (button.id == 40) {
            mc.displayGuiScreen(new GuiMaskTimers(this));
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}
