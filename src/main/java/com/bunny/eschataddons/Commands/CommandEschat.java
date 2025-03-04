package com.bunny.eschataddons.Commands;

import com.bunny.eschataddons.GUI.GuiEschat;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommandEschat extends CommandBase {
    public static boolean queueGUI = false;
    public static long queueTime = 0;
    @Override
    public String getCommandName() {
        return "eschat";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/eschat - Opens the Eschat settings GUI";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        queueGUI = true;
        queueTime = System.currentTimeMillis();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0; // Allow all players to use the command
    }
    /*
        @SubscribeEvent
    public void onTick(TickEvent event) {
        if (!queueGUI) return;
        if (System.currentTimeMillis() - queueTime < 100) return;
        System.out.println("trying to print command");
        Minecraft.getMinecraft().displayGuiScreen(new GuiEschat());
        queueGUI = false;
    }
     */
}

