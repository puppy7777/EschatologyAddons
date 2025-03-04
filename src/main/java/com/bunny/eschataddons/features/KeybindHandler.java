package com.bunny.eschataddons.features;

import com.bunny.eschataddons.GUI.GuiEschat;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class KeybindHandler {
    public KeybindHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
            System.out.println("Keybind pressed! Opening GUI...");
            Minecraft.getMinecraft().displayGuiScreen(new GuiEschat());
        }
    }

    public static class EschatGUI extends CommandBase {
        @Override
        public String getCommandName() {
            return "ea";
        }

        @Override
        public String getCommandUsage(ICommandSender sender) {
            return "open gui";
        }

        @Override
        public void processCommand(ICommandSender sender, String[] args) throws CommandException {
            Minecraft.getMinecraft().displayGuiScreen(new GuiEschat());
        }

        @Override
        public int getRequiredPermissionLevel() {
            return 0;
        }
    }
}
