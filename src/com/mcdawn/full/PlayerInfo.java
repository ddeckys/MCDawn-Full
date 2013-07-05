package com.mcdawn.full;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.mcdawn.full.database.*;

public class PlayerInfo {
	public static List<String> getDevs() { return Arrays.asList("jonnyli1125", "incedo", "ddeckys"); }
	public static Table getTable() { return new Table("Players"); }
	
	private Player player;
	
	public PlayerInfo(Player p) {
		player = p;
	}
	
	public Player getPlayer () {
		return player;
	}
	
	public boolean isDev() {
		return getDevs().contains(player.getName().toLowerCase());
	}
	
	public static PlayerInfo[] getOnlinePlayers() {
		PlayerInfo[] list;
		Player[] plist = Bukkit.getOnlinePlayers();
		list = new PlayerInfo[plist.length];
		for (int t=0; t<plist.length; t++){
			list[t]=new PlayerInfo(plist[t]);
		}
		return list;
	}
	
}
