package com.mcdawn.full;

import java.util.*;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerInfo {
	public static List<String> getDevs() { return Arrays.asList("jonnyli1125", "incedo"); }
	
	public boolean isDev() { return getDevs().contains(thisPlayer.getName().toLowerCase()); }
	
	public Player thisPlayer;
	
	private int permissionLevel;
	
	public int getPerm(){return permissionLevel;}
	
	public void setPerm(int perm){ //Do whatever you do to modify the table :P 
	}
	
	private ChatColor nameColor;
	
	public ChatColor getNameColor(){return nameColor; }
	
	public void setNameColor(ChatColor c){
		//modify table, you might have to convert the enum to a string
		// here is the referenec for ChatColor:
		// http://jd.bukkit.org/rb/apidocs/org/bukkit/ChatColor.html
	}
	
	public PlayerInfo(Player p) { thisPlayer = p;
		//make sure you set the vales of permlevel an namecolor
	}
	
	public void save() {
		
	}
}
