package com.mcdawn.full;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;

public class Util {
	private MCDawn mcdawn;
	
	public static String randomNick() {
		String returnValue = "MC";
		for (int i = 0; i < 4; i++) {
			int randomInt = 1000 + (int)(Math.random() * ((9999 - 1000) + 1));
			returnValue += randomInt;
		}
		return returnValue;
	}
	
	public void setupConfig() {
		FileConfiguration config = mcdawn.getConfig();
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
		mcdawn.saveConfig();
	}
	
	public void saveConfig(Map<String, Object> map) {
		FileConfiguration config = mcdawn.getConfig();
		for (final Entry<String, Object> e : map.entrySet())
			if (!config.contains(e.getKey()))
				config.set(e.getKey(), e.getValue());
		mcdawn.saveConfig();
	}
}
