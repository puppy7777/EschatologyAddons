package com.bunny.eschataddons.Commands;

import com.bunny.eschataddons.config.ConfigHandler;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class NoDT extends CommandBase {

    @Override
    public String getCommandName(){
        return "nodt";
    }

    @Override
    public String getCommandUsage(ICommandSender sender){
        return "/" + this.getCommandName() + " [delay <seconds>]";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender){
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            // Toggle NoDTEnabled
            ConfigHandler.NoDTEnabled = !ConfigHandler.NoDTEnabled;
            ConfigHandler.saveConfig();
            sender.addChatMessage(new ChatComponentText(
                    EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "[EschatAddons] " +
                            EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "No Downtime Enabled: " +
                            EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + ConfigHandler.NoDTEnabled));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delay")) {
            try {
                int delay = Integer.parseInt(args[1]);
                if (delay < 0) throw new NumberFormatException(); // Prevent negative values

                ConfigHandler.NoDTDelay = delay * 1000; // Convert seconds to milliseconds
                ConfigHandler.saveConfig();

                sender.addChatMessage(new ChatComponentText(
                        EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "[EschatAddons] " +
                                EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "NoDT Delay set to: " +
                                EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + delay + " seconds"));

            } catch (NumberFormatException e) {
                sender.addChatMessage(new ChatComponentText(
                        EnumChatFormatting.RED + "Invalid delay value! Please enter a positive integer."));
            }
        } else {
            sender.addChatMessage(new ChatComponentText(
                    EnumChatFormatting.RED + "Invalid usage! Use /nodt or /nodt delay <seconds>."));
        }
    }
}
