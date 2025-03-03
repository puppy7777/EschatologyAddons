package com.bunny.eschatAddons.GUI;

import com.bunny.eschatAddons.config.ConfigHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiSlider;

public class GuiScoreCalc extends GuiScreen {
    private final GuiScreen parentScreen; // To return to the previous screen
    private GuiButton scoreCalcEnable;
    private GuiSlider xPosSlider;
    private GuiSlider yPosSlider;

    public GuiScoreCalc(GuiScreen parent) {
        this.parentScreen = parent;
    }

    @Override
    public void initGui() {
        buttonList.clear();

        // Toggle Buttons
        scoreCalcEnable = new GuiButton(0, width / 2 - 75, height / 2 - 50, 150, 20, getScoreCalcToggleText());
        buttonList.add(scoreCalcEnable);

        // X Position Slider
        xPosSlider = new GuiSlider(1, width / 2 - 75, height / 2 - 25, 150, 20,
                "HUD X: ", "", 0, this.width, ConfigHandler.ScoreCalcHUD_X, false, true,
                new GuiSlider.ISlider() {
                    @Override
                    public void onChangeSliderValue(GuiSlider slider) {
                        ConfigHandler.ScoreCalcHUD_X = (int) slider.getValue();
                    }
                });
        buttonList.add(xPosSlider);

        // Y Position Slider
        yPosSlider = new GuiSlider(4, width / 2 - 75, height / 2, 150, 20,
                "HUD Y: ", "", 0, this.height, ConfigHandler.ScoreCalcHUD_Y, false, true,
                new GuiSlider.ISlider() {
                    @Override
                    public void onChangeSliderValue(GuiSlider slider) {
                        ConfigHandler.ScoreCalcHUD_Y = (int) slider.getValue();
                    }
                });
        buttonList.add(yPosSlider);


        // Back Button
        buttonList.add(new GuiButton(20, width / 2 - 75, height / 2 + 75, 150, 20, "Back"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            ConfigHandler.ScoreCalcEnabled = !ConfigHandler.ScoreCalcEnabled;
            scoreCalcEnable.displayString = getScoreCalcToggleText();
        } else if (button.id == 20) {
            mc.displayGuiScreen(parentScreen); // Return to the previous menu
        }
    }


    private String getScoreCalcToggleText() {
        return "Score calc: " + (ConfigHandler.ScoreCalcEnabled ? "Enabled" : "Disabled");
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
