package com.bunny.eschatAddons.Util;


import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ScoreboardManager {

    public static final Pattern SIDEBAR_EMOJI_PATTERN = Pattern.compile("[\uD83D\uDD2B\uD83C\uDF6B\uD83D\uDCA3\uD83D\uDC7D\uD83D\uDD2E\uD83D\uDC0D\uD83D\uDC7E\uD83C\uDF20\uD83C\uDF6D\u26BD\uD83C\uDFC0\uD83D\uDC79\uD83C\uDF81\uD83C\uDF89\uD83C\uDF82]+");

    private static String scoreboardTitle;
    public static String getScoreboardTitle() {
        return scoreboardTitle;
    }

    private static List<String> scoreboardLines;
    public static List<String> getScoreboardLines() {
        return scoreboardLines;
    }

    private static List<String> tablistLines;
    public static List<String> getTablistLines() {return tablistLines;}

    public static void updateSidebar() {
        //stolen from SBA
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.theWorld == null || mc.isSingleplayer()) {
            clear();
            return;
        }

        Scoreboard scoreboard = mc.theWorld.getScoreboard();
        ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
        if (sidebarObjective == null) {
            clear();
            return;
        }
        // Update titles
        scoreboardTitle = sidebarObjective.getDisplayName();
        // Update score lines
        Collection<Score> scores = scoreboard.getSortedScores(sidebarObjective);
        List<Score> filteredScores = scores.stream().filter(p_apply_1_ -> p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#")).collect(Collectors.toList());
        if (filteredScores.size() > 15) {
            scores = Lists.newArrayList(Iterables.skip(filteredScores, scores.size() - 15));
        } else {
            scores = filteredScores;
        }

        Collections.reverse(filteredScores);

        scoreboardLines = new ArrayList<>();
        for (Score line : scores) {
            ScorePlayerTeam team = scoreboard.getPlayersTeam(line.getPlayerName());
            String scoreboardLine = ScorePlayerTeam.formatPlayerName(team, line.getPlayerName()).trim();
            String cleansedScoreboardLine = SIDEBAR_EMOJI_PATTERN.matcher(scoreboardLine).replaceAll("");
            cleansedScoreboardLine = cleansedScoreboardLine.replaceAll("§." , "");
            scoreboardLines.add(cleansedScoreboardLine);
        }
    }

    public static void updateTablist() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null || mc.getNetHandler() == null) {
            System.out.println("!! Something went wrong trying to get the tablist !!");
        }
        tablistLines = new ArrayList<>();
        for (NetworkPlayerInfo playerInfo : mc.getNetHandler().getPlayerInfoMap()) {
            IChatComponent displayName = playerInfo.getDisplayName(); // Get Hypixel's custom tab name
            if (displayName != null && displayName.getUnformattedText() != "") {
                tablistLines.add(displayName.getUnformattedText()); // Preserve colors & ranks
            }
        }

    }



    private static void clear() {
        scoreboardTitle = null;
        scoreboardLines = null;
        tablistLines = null;
    }

    public static boolean hasScoreboard() {
        return scoreboardTitle != null;
    }

    public static int getNumberOfLines() {
        return scoreboardLines.size();
    }
}