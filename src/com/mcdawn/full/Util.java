package com.mcdawn.full;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;

public class Util {
	public Util() { }
	
	public static String randomNick() {
		String returnValue = "MC";
		for (int i = 0; i < 4; i++) {
			int randomInt = 1000 + (int)(Math.random() * ((9999 - 1000) + 1));
			returnValue += randomInt;
		}
		return returnValue;
	}
	
	public static String parseChat(String message) { return parseChatVariables(parseChatColors(message)); }
	public static String parseChatColors(String message) { return message.replaceAll("&([0-9a-fk-or])+?", "§$1"); }
	public static String parseChatVariables(String message) {
		message = message.replace("$datetime", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		message = message.replace("$date", new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
		message = message.replace("$time", new SimpleDateFormat("HH:mm:ss").format(new Date()));
		message = message.replace("$motd", Bukkit.getServer().getMotd());
		message = message.replace("$server", Bukkit.getServerName());
		message = message.replace("$ip", Bukkit.getIp());
		return message;
	}
	
	public void setupConfig() {
		FileConfiguration config = MCDawn.thisPlugin.getConfig();
		config.options().copyDefaults(true);
		final Map<String, Object> defaults = new HashMap<String, Object>();
		// general
		defaults.put("general.defaultConfig", Color.YELLOW);
		defaults.put("general.autoAfkSet", 10);
		defaults.put("general.autoAfkKick", 20);
		defaults.put("general.allowChatVariables", true);
		defaults.put("general.moneyName", "moneys");
		defaults.put("general.rankChat", true);
		defaults.put("general.forceCuboid", true);
		defaults.put("general.consoleName", "Anonymous");
		defaults.put("general.debugMode", false);
		
		// irc
		defaults.put("irc.useIRC", false);
		defaults.put("irc.server", "irc.esper.net");
		defaults.put("irc.port", 6667);
		defaults.put("irc.channel", "#ChangeMe");
		defaults.put("irc.nick", Util.randomNick());
		defaults.put("irc.identify", "");
		
		// global
		defaults.put("global.useGlobalChat", true);
		defaults.put("global.nick", Util.randomNick());
		defaults.put("global.identify", "");
		
		for (final Entry<String, Object> e : defaults.entrySet())
			if (!config.contains(e.getKey()))
				config.set(e.getKey(), e.getValue());
		MCDawn.thisPlugin.saveConfig();
	}
	
	public void saveConfig(Map<String, Object> map) {
		FileConfiguration config = MCDawn.thisPlugin.getConfig();
		for (final Entry<String, Object> e : map.entrySet())
			if (!config.contains(e.getKey()))
				config.set(e.getKey(), e.getValue());
		MCDawn.thisPlugin.saveConfig();
	}
}
