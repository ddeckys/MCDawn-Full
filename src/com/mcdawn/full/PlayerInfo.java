package com.mcdawn.full;

import java.util.*;

import org.bukkit.entity.Player;

public class PlayerInfo {
	public static List<String> getDevs() { return Arrays.asList("jonnyli1125", "incedo"); }
	
	public boolean isDev() { return getDevs().contains(thisPlayer.getName().toLowerCase()); }
	
	public Player thisPlayer;
	
	public PlayerInfo(Player p) { thisPlayer = p; }
	
	public void save() {
		
	}
}
