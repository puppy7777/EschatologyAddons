package com.bunny.eschataddons;

import com.bunny.eschataddons.Commands.CommandEschat;
import com.bunny.eschataddons.HUD.ScoreCalcHUD;
import com.bunny.eschataddons.config.ConfigHandler;
import com.bunny.eschataddons.features.*;
import com.bunny.eschataddons.HUD.NoDTHUD;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = eschatAddons.MODID, version = eschatAddons.VERSION)
public class eschatAddons {
    public static final String MODID = "eschatAddons";
    public static final String VERSION = "0.1";

    @Mod.Instance
    public static eschatAddons instance; // Ensure instance is available

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("[eschatAddons] Pre-initialization started...");
        ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("[eschatAddons] Registering event handlers...");

        // Register keybinds
        MinecraftForge.EVENT_BUS.register(new KeybindHandler());

        // Register other event handlers
        MinecraftForge.EVENT_BUS.register(new PartyCommands());
        MinecraftForge.EVENT_BUS.register(new NoDTHUD());
        MinecraftForge.EVENT_BUS.register(new ScoreCalcHUD());
        MinecraftForge.EVENT_BUS.register(new ScoreCalc());

        MinecraftForge.EVENT_BUS.register(new CommandEschat());
        ClientCommandHandler.instance.registerCommand(new KeybindHandler.EschatGUI());
        ClientCommandHandler.instance.registerCommand(new ScoreCalc.ScoreboardTest());
    }
}
