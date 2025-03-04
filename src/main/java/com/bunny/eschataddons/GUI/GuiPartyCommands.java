package com.bunny.eschataddons.GUI;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import com.bunny.eschataddons.config.ConfigHandler;

public class GuiPartyCommands extends GuiScreen {
    private final GuiScreen parentScreen;
    private GuiButton PartyCommandsEnabled;
    private GuiButton PartyInvitesEnabled;

    public GuiPartyCommands(GuiScreen parent){
        this.parentScreen = parent;
    }

    @Override
    public void initGui() {
        buttonList.clear();

        // Toggle Buttons
        PartyCommandsEnabled = new GuiButton(0, width / 2 - 100, height / 2 - 25, 200, 20, getPartyCommandsEnabledText());
        buttonList.add(PartyCommandsEnabled);

        PartyInvitesEnabled = new GuiButton(1, width / 2 - 100, height / 2, 200, 20, getPartyInvitesEnabledText());
        buttonList.add(PartyInvitesEnabled);


        buttonList.add(new GuiButton(20, width / 2 - 100, height / 2 + 25, 200, 20, "Back"));

    }

    @Override
    protected void actionPerformed(GuiButton button){
        if (button.id == 0){
            ConfigHandler.PartyCommandsEnabled = !ConfigHandler.PartyCommandsEnabled;
            PartyCommandsEnabled.displayString = getPartyCommandsEnabledText();
        } else if (button.id == 1){
            ConfigHandler.PartyInvEnabled = !ConfigHandler.PartyInvEnabled;
            PartyInvitesEnabled.displayString = getPartyInvitesEnabledText();
        } else if (button.id == 20) {
            mc.displayGuiScreen(parentScreen); // Return to the previous menu
        }
    }

    private String getPartyCommandsEnabledText(){
        return "Party Commands: " + (ConfigHandler.PartyCommandsEnabled ? "Enabled" : "Disabled");
    }

    private String getPartyInvitesEnabledText(){
        return "Private Party Invites: " + (ConfigHandler.PartyInvEnabled ? "Enabled" : "Disabled");
    }

    @Override
    public void onGuiClosed() {
        ConfigHandler.saveConfig();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}