package com.bunny.eschataddons.GUI;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiSlider;
import com.bunny.eschataddons.config.ConfigHandler;

public class GuiNoDTSettings extends GuiScreen {
    private final GuiScreen parentScreen; // To return to the previous screen
    private GuiButton noDTEnable;
    private GuiButton noDTHudEnable;
    private GuiSlider delaySlider;
    private GuiSlider xPosSlider;
    private GuiSlider yPosSlider;

    public GuiNoDTSettings(GuiScreen parent) {
        this.parentScreen = parent;
    }

    @Override
    public void initGui() {
        buttonList.clear();

        // Toggle Buttons
        noDTEnable = new GuiButton(0, width / 2 - 75, height / 2 - 50, 150, 20, getNoDtToggleText());
        buttonList.add(noDTEnable);

        noDTHudEnable = new GuiButton(1, width / 2 - 75, height / 2 - 25, 150, 20, getNoDtHUDToggleText());
        buttonList.add(noDTHudEnable);

        // Delay Slider
        delaySlider = new GuiSlider(2, width / 2 - 75, height / 2, 150, 20,
                "Auto Requeue Delay: ", "s", 0, 10, ConfigHandler.NoDTDelay / 1000, false, true,
                new GuiSlider.ISlider() {
                    @Override
                    public void onChangeSliderValue(GuiSlider slider) {
                        ConfigHandler.NoDTDelay = (int) slider.getValue() * 1000;
                    }
                });
        buttonList.add(delaySlider);

        // X Position Slider
        xPosSlider = new GuiSlider(3, width / 2 - 75, height / 2 + 25, 150, 20,
                "HUD X: ", "", 0, this.width, ConfigHandler.NoDTHUD_X, false, true,
                new GuiSlider.ISlider() {
                    @Override
                    public void onChangeSliderValue(GuiSlider slider) {
                        ConfigHandler.NoDTHUD_X = (int) slider.getValue();
                    }
                });
        buttonList.add(xPosSlider);

        // Y Position Slider
        yPosSlider = new GuiSlider(4, width / 2 - 75, height / 2 + 50, 150, 20,
                "HUD Y: ", "", 0, this.height, ConfigHandler.NoDTHUD_Y, false, true,
                new GuiSlider.ISlider() {
                    @Override
                    public void onChangeSliderValue(GuiSlider slider) {
                        ConfigHandler.NoDTHUD_Y = (int) slider.getValue();
                    }
                });
        buttonList.add(yPosSlider);


        // Back Button
        buttonList.add(new GuiButton(20, width / 2 - 75, height / 2 + 75, 150, 20, "Back"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            ConfigHandler.NoDTEnabled = !ConfigHandler.NoDTEnabled;
            noDTEnable.displayString = getNoDtToggleText();
        } else if (button.id == 1) {
            ConfigHandler.NoDTHUD = !ConfigHandler.NoDTHUD;
            noDTHudEnable.displayString = getNoDtHUDToggleText();
        } else if (button.id == 20) {
            mc.displayGuiScreen(parentScreen); // Return to the previous menu
        }
    }

    private String getNoDtToggleText() {
        return "Auto Requeue: " + (ConfigHandler.NoDTEnabled ? "Enabled" : "Disabled");
    }

    private String getNoDtHUDToggleText() {
        return "NoDT HUD: " + (ConfigHandler.NoDTHUD ? "Enabled" : "Disabled");
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
