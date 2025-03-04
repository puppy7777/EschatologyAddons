package com.bunny.eschataddons.config;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class ConfigHandler {

    private static Configuration config;

    // No Downtime Config
    public static boolean NoDTEnabled;
    public static int NoDTDelay;
    public static boolean NoDTHUD;
    public static int NoDTHUD_X;
    public static int NoDTHUD_Y;

    // Party Commands
    public static boolean PartyCommandsEnabled;
    public static boolean PartyInvEnabled;
    public static boolean DungeonFloorCommandsEnabled;

    // Scorecalc
    public static boolean ScoreCalcEnabled;
    public static int ScoreCalcHUD_X;
    public static int ScoreCalcHUD_Y;

    // mask timers
    public static boolean MaskTimersEnabled;
    public static int MaskTimersHUD_X;
    public static int MaskTimersHUD_Y;


    public static void loadConfig(File file) {
        config = new Configuration(file);

        try {
            config.load();
        } catch (Exception e) {
            System.err.println("Failed to load configuration file: " + file.getName());
            e.printStackTrace();
        }

        // No DT
        NoDTEnabled = config.get("NoDT", "Enabled", false, "Is No Downtime Enabled?").getBoolean();
        NoDTDelay = config.get("NoDT", "Delay", 0, "How long is the delay before entering a new run?").getInt();
        NoDTHUD = config.get("NoDT", "HUD", false, "No Downtime Enabled/Disabled HUD").getBoolean();
        NoDTHUD_X = config.get("NoDT", "X_Position", 50, "The X position of the NoDT HUD").getInt();
        NoDTHUD_Y = config.get("NoDT", "Y_Position", 50, "The Y position of the NoDT HUD").getInt();

        // Party Commands
        PartyCommandsEnabled = config.get("PartyCommands", "Enabled", false, "Are the party commands Enabled?").getBoolean();
        PartyInvEnabled = config.get("PartyCommands", "InvEnabled", false, "Is !inv enabled?").getBoolean();

        // dungeon floor commands
        DungeonFloorCommandsEnabled = config.get("Dungeon Floor Commands", "Enabled", true, "!f1-7, !m1-7 enabled?").getBoolean();

        //scorecalc
        ScoreCalcEnabled = config.get("ScoreCalc", "Enabled", true, "Is score calc enabled?").getBoolean();
        ScoreCalcHUD_X = config.get("ScoreCalc", "X_Position", 50, "Scorecalc HUD X position").getInt();
        ScoreCalcHUD_Y = config.get("ScoreCalc", "Y_Position", 50, "Scorecalc HUD Y position").getInt();


        MaskTimersEnabled = config.get("MaskTimers", "Enabled", true, "Mask timers enabled?").getBoolean();
        MaskTimersHUD_X = config.get("MaskTimers", "X_Position", 50, "MaskTimers HUD X position").getInt();
        MaskTimersHUD_Y = config.get("MaskTimers", "Y_Position", 50, "MaskTimers HUD Y position").getInt();
    }

    public static void saveConfig() {
        if (config == null) {
            System.err.println("Config not initialized. Call loadConfig() first.");
            return;
        }

        // NoDT
        config.get("NoDT", "Enabled", NoDTEnabled, "Is No Downtime Enabled?").set(NoDTEnabled);
        config.get("NoDT", "Delay", NoDTDelay, "How long is the delay before entering a new run?").set(NoDTDelay);
        config.get("NoDT", "HUD", NoDTHUD, "No Downtime Enabled/Disabled HUD").set(NoDTHUD);
        config.get("NoDT", "X_Position", NoDTHUD_X, "The X position of the NoDT HUD").set(NoDTHUD_X);
        config.get("NoDT", "Y_Position", NoDTHUD_Y, "The Y position of the NoDT HUD").set(NoDTHUD_Y);

        // Party Commands
        config.get("PartyCommands", "Enabled", PartyCommandsEnabled, "Are the party commands Enabled?").set(PartyCommandsEnabled);
        config.get("PartyCommands", "InvEnabled", PartyInvEnabled, "Is !inv enabled?").set(PartyInvEnabled);

        //dungeon floor commands
        config.get("Dungeon Floor Commands", "Enabled", DungeonFloorCommandsEnabled, "!f1-7, !m1-7 enabled?").set(DungeonFloorCommandsEnabled);

        // score calc
        config.get("ScoreCalc", "Enabled", ScoreCalcEnabled, "Is score calc enabled?").set(ScoreCalcEnabled);
        config.get("ScoreCalc", "X_Position", ScoreCalcHUD_X, "Scorecalc HUD X position").set(ScoreCalcHUD_X);
        config.get("ScoreCalc", "Y_Position", ScoreCalcHUD_Y, "Scorecalc HUD Y position").set(ScoreCalcHUD_Y);

        // mask timers
        config.get("MaskTimers", "Enabled", MaskTimersEnabled, "Mask timers enabled?").set(MaskTimersEnabled);
        config.get("MaskTimers", "X_Position", MaskTimersHUD_X, "MaskTimers HUD X position").set(MaskTimersHUD_X);
        config.get("MaskTimers", "Y_Position", MaskTimersHUD_Y, "MaskTimers HUD Y position").set(MaskTimersHUD_Y);



        config.save();
        System.out.println("Configuration saved successfully.");
    }
}
