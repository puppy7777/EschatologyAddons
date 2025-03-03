package com.bunny.eschatAddons;

import com.bunny.eschatAddons.Commands.CommandEschat;
import com.bunny.eschatAddons.HUD.ScoreCalcHUD;
import com.bunny.eschatAddons.config.ConfigHandler;
import com.bunny.eschatAddons.features.*;
import com.bunny.eschatAddons.HUD.NoDTHUD;
import com.bunny.eschatAddons.HUD.ScoreCalcHUD;
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
        MinecraftForge.EVENT_BUS.register(new NoDTListener());
        MinecraftForge.EVENT_BUS.register(new PartyCommands());
        MinecraftForge.EVENT_BUS.register(new NoDTHUD());
        MinecraftForge.EVENT_BUS.register(new ScoreCalcHUD());
        MinecraftForge.EVENT_BUS.register(new DungeonFloorCommands());
        MinecraftForge.EVENT_BUS.register(new ScoreCalc());

        MinecraftForge.EVENT_BUS.register(new CommandEschat());
        ClientCommandHandler.instance.registerCommand(new ScoreCalc.ScoreboardTest());
    }
}
