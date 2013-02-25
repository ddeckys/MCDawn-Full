package com.mcdawn.full;

import java.util.*;

import org.bukkit.entity.Player;

import com.mcdawn.full.database.*;

public class PlayerInfo {
	public static List<String> getDevs() { return Arrays.asList("jonnyli1125", "incedo"); }
	public static Table getTable() { return new Table("Players"); }
	
	public Player thisPlayer;
	
	public PlayerInfo(Player p) { thisPlayer = p; }
	public boolean isDev() { return getDevs().contains(thisPlayer.getName().toLowerCase()); }
	
}
