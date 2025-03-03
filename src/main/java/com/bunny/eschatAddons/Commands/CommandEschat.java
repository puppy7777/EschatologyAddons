package com.bunny.eschatAddons.Commands;

import com.bunny.eschatAddons.GUI.GuiEschat;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommandEschat extends CommandBase {

    @Override
    public String getCommandName() {
        return "eschat";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/eschat - Opens the Eschat settings GUI";
    }

    @Override
    @SideOnly(Side.CLIENT) // Ensures this runs only on the client
    public void processCommand(ICommandSender sender, String[] args) {
        System.out.println("Command received!");
        System.out.println("Command executed on thread: " + Thread.currentThread().getName());

        // Ensure this runs on the main Minecraft thread
        final Minecraft mc = Minecraft.getMinecraft();
        if (!mc.isCallingFromMinecraftThread()) {
            System.out.println("Command is running on the wrong thread! Fixing...");

            mc.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    openGui();
                }
            });
            return;
        }

        openGui();
    }

    private void openGui() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) {
            System.out.println("Error: Cannot open GUI, world or player is null");
            return;
        }
        System.out.println("Opening GUI from correct thread!");
        mc.displayGuiScreen(new GuiEschat());
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0; // Allow all players to use the command
    }
}