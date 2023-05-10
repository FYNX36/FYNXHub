package org.fynx.fynxhub.scoreboard;

import org.bukkit.ChatColor;

public enum EntryName {

    ENTRY_0(0, ChatColor.DARK_GRAY.toString()),
    ENTRY_1(1, ChatColor.GREEN.toString()),
    ENTRY_2(2, ChatColor.RED.toString()),
    ENTRY_3(3, ChatColor.BOLD.toString()),
    ENTRY_4(4, ChatColor.AQUA.toString()),
    ENTRY_5(5, ChatColor.DARK_PURPLE.toString()),
    ENTRY_6(6, ChatColor.MAGIC.toString()),
    ENTRY_7(7, ChatColor.WHITE.toString()),
    ENTRY_8(8, ChatColor.ITALIC.toString()),
    ENTRY_9(9, ChatColor.DARK_BLUE.toString()),
    ENTRY_10(10, ChatColor.LIGHT_PURPLE.toString());

    private final int entry;
    private final String entryName;

    EntryName(int entry, String entryName) {
        this.entry = entry;
        this.entryName = entryName;
    }


    public String getEntryName() {
        return entryName;
    }

    public int getEntry() {
        return entry;
    }

}
