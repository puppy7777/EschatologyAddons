package com.bunny.eschataddons.GUI;

import com.bunny.eschataddons.config.ConfigHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiSlider;

public class GuiMaskTimers extends GuiScreen {
    private final GuiScreen parentScreen; // To return to the previous screen
    private GuiButton maskTimerEnable;
    private GuiSlider xPosSlider;
    private GuiSlider yPosSlider;
    public static boolean editingMaskTimer = false;
    public GuiMaskTimers(GuiScreen parent) {
        this.parentScreen = parent;
    }

    @Override
    public void initGui() {
        buttonList.clear();
        editingMaskTimer = true;
        // Toggle Buttons
        maskTimerEnable = new GuiButton(0, width / 2 - 75, height / 2 - 50, 150, 20, getMaskTimerToggleText());
        buttonList.add(maskTimerEnable);

        // X Position Slider
        xPosSlider = new GuiSlider(1, width / 2 - 75, height / 2 - 25, 150, 20,
                "HUD X: ", "", 0, this.width, ConfigHandler.MaskTimersHUD_X, false, true,
                new GuiSlider.ISlider() {
                    @Override
                    public void onChangeSliderValue(GuiSlider slider) {
                        ConfigHandler.MaskTimersHUD_X = (int) slider.getValue();
                    }
                });
        buttonList.add(xPosSlider);

        // Y Position Slider
        yPosSlider = new GuiSlider(4, width / 2 - 75, height / 2, 150, 20,
                "HUD Y: ", "", 0, this.height, ConfigHandler.MaskTimersHUD_Y, false, true,
                new GuiSlider.ISlider() {
                    @Override
                    public void onChangeSliderValue(GuiSlider slider) {
                        ConfigHandler.MaskTimersHUD_Y = (int) slider.getValue();
                    }
                });
        buttonList.add(yPosSlider);


        // Back Button
        buttonList.add(new GuiButton(20, width / 2 - 75, height / 2 + 75, 150, 20, "Save"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            ConfigHandler.MaskTimersEnabled = !ConfigHandler.MaskTimersEnabled;
            maskTimerEnable.displayString = getMaskTimerToggleText();
        } else if (button.id == 20) {
            editingMaskTimer = false;
            mc.displayGuiScreen(parentScreen); // Return to the previous menu
        }
    }


    private String getMaskTimerToggleText() {
        return "Mask Timers: " + (ConfigHandler.MaskTimersEnabled ? "Enabled" : "Disabled");
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
