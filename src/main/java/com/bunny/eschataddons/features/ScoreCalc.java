package com.bunny.eschataddons.features;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.bunny.eschataddons.Util.ScoreboardManager;

import java.util.List;
import java.util.regex.Pattern;

import static java.lang.Math.*;

public class ScoreCalc {

    public static double dungeonFloorPercent = -1;
    public static boolean mimicDead = false;
    public static long lastInDungeonCheck = 0;
    public static long lastScoreCalc = 0;
    public static int[] dungeonScore = {-1,0};
    public ScoreCalc() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent chat) {
        if (chat.message.getUnformattedText().contains("Mimic")) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Got msg containing M, assuming dead"));
            mimicDead = true;
        }
    }

    @SubscribeEvent
    public void onLoad(WorldEvent.Load world) {
        mimicDead = false;
    }

    // secrets%, secrets#, crypts, puzzlesDone, puzzlesTotal, numDeaths
    public static double[] getDungeonClear() {
        double[] dungeonClear = {0,0,0,0,0,0};
        Minecraft minecraft = Minecraft.getMinecraft();
        ScoreboardManager.updateTablist();
        List<String> tablistLines = ScoreboardManager.getTablistLines();
        //get secret %, secret crypts, puzzles
        for (String tabline: tablistLines) {
            //secrets
            if (tabline.contains("Secrets Found: ")) {
                String[] split = tabline.split(": ");
                if (tabline.contains("%")) {
                    try {
                        split[1] = split[1].substring(0, split[1].length() - 1);
                        dungeonClear[0] = Double.parseDouble(split[1]);
                    } catch (Exception uhoh) {
                        minecraft.thePlayer.addChatMessage(new ChatComponentText("failed to get secret percent"));
                    }
                }
                else {
                    try {
                        dungeonClear[1] = Double.parseDouble(split[1]);
                    } catch (Exception uhoh) {
                        minecraft.thePlayer.addChatMessage(new ChatComponentText("failed to get secret count"));
                    }
                }
            }
            else if (tabline.contains("Crypts:")) {
                String[] split = tabline.split(": ");
                try {
                    dungeonClear[2] = Integer.parseInt(split[1]);
                } catch (Exception uhoh) {
                    minecraft.thePlayer.addChatMessage(new ChatComponentText("failed to get secret count"));
                }
            }
            else if (Pattern.compile("\\[.\\]").matcher(tabline).find()) {
                dungeonClear[4]++;
                String[] split = tabline.split("\\[");
                try {
                    if (split[1].charAt(0) == '✔') {
                        dungeonClear[3]++;
                    }
                } catch (Exception uhoh) {
                    minecraft.thePlayer.addChatMessage(new ChatComponentText("failed to get completed puzzles"));
                }
            }
            else if (tabline.contains("Team Deaths")) {
                String[] split = tabline.split(": ");
                try {
                    dungeonClear[5] = Integer.parseInt(split[1]);
                } catch (Exception uhoh) {
                    minecraft.thePlayer.addChatMessage(new ChatComponentText("failed to get death count"));
                }
            }
        }
        return dungeonClear;
    }

    public static void updateScore() {
        if (System.currentTimeMillis() - lastScoreCalc < 500) {
            return;
        }
        lastScoreCalc = System.currentTimeMillis();
        updateFloor();
        if (dungeonFloorPercent == -1) {
            mimicDead = false;
            return;
        }
        double[] dungeonClear = getDungeonClear();
        // secrets%, secrets#, crypts, puzzlesDone, puzzlesTotal, numDeaths
        //not calculating speed score: imagine skill issuing that hard :_)
        int skill, explore, speed = 100, bonus;
        //skill = 20 + 80 points for completed rooms - 10 per failed puzzle - death penalty
        //we assume all rooms are cleared but not all puzzles are done (same as bettermaps)
        //assume spirit
        int deathPenalty = max(0, (int) (dungeonClear[5] * 2) - 1);
        skill = 100 - 10 * ((int) dungeonClear[4] - (int) dungeonClear[3]) - deathPenalty;
        //secret score is 60 points for completed rooms + 40 * secret % completion compared to max needed
        //once again we will be assuming all rooms are cleared
        double secretScore = 0;
        try {
            //dungeonFloorPercent is 0-1 based on what proportion secrets needed for full secret score
            secretScore = (min((dungeonClear[0] / 100 * dungeonFloorPercent), 1)) * 40;
        } catch (Exception ignored) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Failed to calc secret score"));
        }
        explore = 60 + (int) floor(secretScore);
        bonus = (int) (min(dungeonClear[2], 5));
        if (mimicDead) {
            bonus += 2;
        }
        dungeonScore[0] = skill + explore + speed + bonus;

        //get estimated remaining secrets needed
        if (dungeonClear[0] == 0 || dungeonClear[1] == 0) {
            dungeonScore[1] = -1;
            return;
        }
        else {
            double secretScoreNeeded = min(40 - bonus + deathPenalty, 40);
            int estimatedTotalSecretCount = -1;
            try {
                estimatedTotalSecretCount = (int) round(100 / (dungeonClear[0] / dungeonClear[1]));
            } catch (Exception ignored) {
                dungeonScore[1] = -1;
                return;
            }
            //formula: floor (totalneeded * (score + 1) / 40 * total secrets)
            int secretsRemaining = (int) floor(dungeonFloorPercent * (secretScoreNeeded + 1) / 40 * estimatedTotalSecretCount);
            if (floor((double) (secretsRemaining - 1) / estimatedTotalSecretCount * 40) == secretScoreNeeded) {
                secretsRemaining--;
            }
            dungeonScore[1] = max(secretsRemaining - (int) dungeonClear[1], 0);
            return;
        }
    }

    public static void updateFloor() {
        // -1 for not in dungeon, 1-7 standard, screw entrance
        //3 seconds between each dungeon check
        if (System.currentTimeMillis() - lastInDungeonCheck < 3000) {
            return;
        }
        lastInDungeonCheck = System.currentTimeMillis();
        ScoreboardManager.updateSidebar();
        List<String> scoreboardLines = ScoreboardManager.getScoreboardLines();
        try {
            String floor = scoreboardLines.get(4);
            //check if we're in the catacombs and what our secret clear should be
            //default value is 100%, only need to check if that's not the case
            if (floor.contains("The Catacombs")) {
                String[] segments = floor.split("\\(");
                if (segments[1].charAt(0) == 'F') {
                    switch (segments[1].charAt(1)) {
                        case '1':
                            dungeonFloorPercent = .3;
                            break;
                        case '2':
                            dungeonFloorPercent = .4;
                            break;
                        case '3':
                            dungeonFloorPercent = .5;
                            break;
                        case '4':
                            dungeonFloorPercent = .6;
                            break;
                        case '5':
                            dungeonFloorPercent = .7;
                            break;
                        case '6':
                            dungeonFloorPercent = .85;
                            break;
                        default:
                            dungeonFloorPercent = 1;
                    }
                    return;
                }
                else {
                    //we are not in regular floor, assume master mode (screw entrance)
                    dungeonFloorPercent = 1;
                    return;
                }
            }
            else {
                //we are not in a dungeon
                mimicDead = false;
                dungeonFloorPercent = -1;
                return;
            }
        } catch (Exception ignored) {}
        dungeonFloorPercent = -1;
        //assume not in dungeon
    }
    public static class ScoreboardTest extends CommandBase {
        @Override
        public String getCommandName() {
            return "sbtest";
        }

        @Override
        public String getCommandUsage(ICommandSender sender) {
            return "test thing for scoreboard calc";
        }

        @Override
        public void processCommand(ICommandSender sender, String[] args) throws CommandException {
            double[] dungeonClear = getDungeonClear();
            for (double num : dungeonClear) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.valueOf(num)));
            }
            updateScore();
            int[] scoreInfo = ScoreCalc.dungeonScore;
        }

        @Override
        public int getRequiredPermissionLevel() {
            return 0;
        }
    }
}