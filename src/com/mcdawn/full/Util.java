package com.mcdawn.full;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;

import com.maxmind.geoip.*;
import com.mcdawn.full.commands.*;

public class Util {
	public String[] getAllCommands() {
		ArrayList<String> commands = new ArrayList<String>();
		for (String s : new Building().getAllCommands()) commands.add(s);
		return (String[]) commands.toArray();
	}
	
	public void initializeCommands() {
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
	
	public static Boolean isLocalhostIP(String ipAddress) {
		String[] localhostIPs = new String[] { "localhost", "127.0.0.", "192.168.0.", "10.10.10." };
		for (String s : localhostIPs)
			if (ipAddress.startsWith(s))
				return true;
		return false;
	}
	
	public static String downloadString(String url) {
		String s = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			String ss;
			while ((ss = reader.readLine()) != null) s += ss;
		} catch (IOException ex) { ex.printStackTrace(); }
        return s;
	}
	
	public static void downloadFile(String url, String fileName) {
        try {
        	URLConnection urlCon = new URL(url).openConnection();
        	InputStream is = urlCon.getInputStream();
        	FileOutputStream fos = new FileOutputStream(fileName);
        	byte[] buffer = new byte[1000];         
        	int bytesRead = is.read(buffer);
        	while (bytesRead > 0) {
        		fos.write(buffer, 0, bytesRead);
        		bytesRead = is.read(buffer);
        	}
        	is.close();
        	fos.close();
        } catch (IOException ex) { ex.printStackTrace(); }
	}
	
	public static String getPublicIP() {
		String raw = downloadString("http://checkip.dyndns.org/");
		raw = raw.substring(raw.indexOf(":") + 1).trim();
		raw = raw.substring(0, raw.indexOf("<")).trim();
		return raw;
	}
	
	public static String getCountry(String ipAddress) {
		try {
			if (!new File("plugins/MCDawn/GeoIP.dat").exists())
				downloadFile("http://updates.mcdawn.com/dll/GeoIP.dat", "plugins/MCDawn/GeoIP.dat");
			LookupService ls = new LookupService("plugins/MCDawn/GeoIP.dat");
			return isLocalhostIP(ipAddress) ? "Localhost (" + ls.getCountry(getPublicIP()).getName() + ")" : ls.getCountry(ipAddress).getName();
		}
		catch (Exception ex) { ex.printStackTrace(); return "N/A"; }
	}
	
	public static void setupConfig() {
		FileConfiguration config = MCDawn.thisPlugin.getConfig();
		config.options().copyDefaults(true);
		final Map<String, Object> defaults = new HashMap<String, Object>();
		// general
		defaults.put("general.autoAfkSet", 10);
		defaults.put("general.autoAfkKick", 20);
		defaults.put("general.rankChat", true);
		defaults.put("general.forceCuboid", true);
		defaults.put("general.consoleName", "Anonymous");
		defaults.put("general.debugMode", false);
		
		// irc
		defaults.put("irc.useIRC", false);
		defaults.put("irc.server", "irc.esper.net");
		defaults.put("irc.port", 6667);
		defaults.put("irc.password", "");
		defaults.put("irc.channel", "#ChangeMe");
		defaults.put("irc.nick", Util.randomNick());
		defaults.put("irc.identify", "");
		
		// global
		defaults.put("global.useGlobalChat", true);
		defaults.put("global.nick", Util.randomNick());
		defaults.put("global.identify", "");
		
		saveConfig(defaults);
	}
	
	public static void saveConfig(Map<String, Object> map) {
		FileConfiguration config = MCDawn.thisPlugin.getConfig();
		for (final Entry<String, Object> e : map.entrySet())
			if (!config.contains(e.getKey()))
				config.set(e.getKey(), e.getValue());
		MCDawn.thisPlugin.saveConfig();
	}
	
	public static int randomInt(int min, int max) { return min + (int)(Math.random() * ((max - min) + 1)); }
	
	public static String randomNick() { return "MC" + randomInt(1000, 9999); }
}
