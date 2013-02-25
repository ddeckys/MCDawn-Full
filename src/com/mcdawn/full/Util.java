package com.mcdawn.full;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.maxmind.geoip.*;
import com.mcdawn.full.commands.*;
import com.mcdawn.full.database.*;

public class Util {
	public static void setupCommands() {
		BuildingCommands b = new BuildingCommands();
		for (String s : b.getAllCommands()) MCDawn.thisPlugin.getCommand(s).setExecutor(b);
		InformationCommands i = new InformationCommands();
		for (String s : i.getAllCommands()) MCDawn.thisPlugin.getCommand(s).setExecutor(i);
		ModerationCommands m = new ModerationCommands();
		for (String s : m.getAllCommands()) MCDawn.thisPlugin.getCommand(s).setExecutor(m);
		OtherCommands o = new OtherCommands();
		for (String s : o.getAllCommands()) MCDawn.thisPlugin.getCommand(s).setExecutor(o);
	}
	
	public static String capitalizeFirstChar(String s) { return s.substring(0, 1).toUpperCase() + s.substring(1); }
	
	public static String parseChat(String message) { return parseChatVariables(ChatColor.translateAlternateColorCodes('&', message)); }
	public static String parseChatVariables(String message) {
		message = message.replace("$datetime", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		message = message.replace("$date", new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
		message = message.replace("$time", new SimpleDateFormat("HH:mm:ss").format(new Date()));
		message = message.replace("$motd", Bukkit.getServer().getMotd());
		message = message.replace("$server", Bukkit.getServerName());
		message = message.replace("$ip", Bukkit.getIp());
		return message;
	}
	
	public static final char IRC_COLOR_CODE = '\u0003';
	public static final char IRC_BOLD_CODE = '\u0002';
	public static final char IRC_STRIKETHROUGH_CODE = '\u0013';
	public static final char IRC_UNDERLINE_CODE = '\u0015';
	public static final char IRC_ITALIC_CODE = '\u0009';
	public static final char IRC_RESET_CODE = '\u000f';
	
	@SuppressWarnings("serial")
	public static HashMap<String, String> getMinecraftIRCColorMap() {
		return new HashMap<String, String>() {{
			put(ChatColor.COLOR_CHAR + "0", IRC_COLOR_CODE + "1");
			put(ChatColor.COLOR_CHAR + "1", IRC_COLOR_CODE + "2");
			put(ChatColor.COLOR_CHAR + "2", IRC_COLOR_CODE + "3");
			put(ChatColor.COLOR_CHAR + "3", IRC_COLOR_CODE + "10");
			put(ChatColor.COLOR_CHAR + "4", IRC_COLOR_CODE + "5");
			put(ChatColor.COLOR_CHAR + "5", IRC_COLOR_CODE + "6");
			put(ChatColor.COLOR_CHAR + "6", IRC_COLOR_CODE + "7");
			put(ChatColor.COLOR_CHAR + "7", IRC_COLOR_CODE + "15");
			put(ChatColor.COLOR_CHAR + "8", IRC_COLOR_CODE + "14");
			put(ChatColor.COLOR_CHAR + "9", IRC_COLOR_CODE + "12");
			put(ChatColor.COLOR_CHAR + "a", IRC_COLOR_CODE + "9");
			put(ChatColor.COLOR_CHAR + "b", IRC_COLOR_CODE + "11");
			put(ChatColor.COLOR_CHAR + "c", IRC_COLOR_CODE + "4");
			put(ChatColor.COLOR_CHAR + "d", IRC_COLOR_CODE + "13");
			put(ChatColor.COLOR_CHAR + "e", IRC_COLOR_CODE + "8");
			put(ChatColor.COLOR_CHAR + "f", IRC_COLOR_CODE + "0");
			put(ChatColor.COLOR_CHAR + "l", String.valueOf(IRC_BOLD_CODE));
			put(ChatColor.COLOR_CHAR + "m", String.valueOf(IRC_STRIKETHROUGH_CODE));
			put(ChatColor.COLOR_CHAR + "n", String.valueOf(IRC_UNDERLINE_CODE));
			put(ChatColor.COLOR_CHAR + "o", String.valueOf(IRC_ITALIC_CODE));
			put(ChatColor.COLOR_CHAR + "r", String.valueOf(IRC_RESET_CODE));
		}};
	}
	
	public static String minecraftToIRC(String message) {
		for (Entry<String, String> e : getMinecraftIRCColorMap().entrySet())
			message = message.replace(e.getKey(), e.getValue());
		return message;
	}
	
	public static String ircToMinecraft(String message) {
		for (Entry<String, String> e : getMinecraftIRCColorMap().entrySet())
			message = message.replace(e.getValue(), e.getKey());
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
		return raw;}
	
	public static String getCountry(String ipAddress) {
		try {
			if (!new File("plugins/MCDawn/GeoIP.dat").exists())
				downloadFile("http://updates.mcdawn.com/dll/GeoIP.dat", "plugins/MCDawn/GeoIP.dat");
			LookupService ls = new LookupService("plugins/MCDawn/GeoIP.dat");
			return isLocalhostIP(ipAddress) ? "Localhost (" + ls.getCountry(getPublicIP()).getName() + ")" : ls.getCountry(ipAddress).getName();
		} catch (Exception ex) { ex.printStackTrace(); return "N/A"; }
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
		defaults.put("general.consoleName", "Owner");
		defaults.put("general.debugMode", false);
		
		// irc
		defaults.put("irc.useIRC", false);
		defaults.put("irc.server", "irc.esper.net");
		defaults.put("irc.port", 6667);
		defaults.put("irc.password", "");
		defaults.put("irc.channel", "#ChangeMe");
		defaults.put("irc.nick", Util.randomNick());
		defaults.put("irc.identify", "");
		defaults.put("irc.color", ChatColor.BLUE.getChar());
		
		// globalchat
		defaults.put("globalchat.useGlobalChat", true);
		defaults.put("globalchat.nick", Util.randomNick());
		defaults.put("globalchat.identify", "");
		defaults.put("globalchat.color", ChatColor.DARK_AQUA.getChar());
		
		saveConfig(defaults);
	}
	
	@SuppressWarnings("serial")
	public static void setupDatabase() {
		Database.initialize();
		Table playersTable = new Table("Players");
		Map<String, String> playersTableColumns = new HashMap<String, String>() {{
			put("Username", "");
			put("DisplayName", "");
			put("IPAddress", "");
			put("Country", "");
			put("TimeSpent", "0");
			put("Hidden", "False");
			put("Invincible", "False");
			put("Muted", "False");
			put("MutedBy", "");
			put("Frozen", "False");
			put("FrozenBy", "");
			put("Deafened", "False");
			put("DeafenedBy", "");
			put("Jailed", "False");
			put("JailedBy", "");
			put("Handcuffed", "False");
			put("HandcuffedBy", "");
			put("Warnings", "0");
			put("WarnedBy", "");
			put("WarningRevokedBy", "");
			put("FirstLoggedIn", "");
			put("LastLoggedIn", "");
			put("Logins", "0");
			put("ModifiedBlocks", "0");
			put("DestroyedBlocks", "0"); // BuiltBlocks column unneeded
			put("Kicked", "0");
			put("Deaths", "0");
			put("Money", "0");
		}};
		try {
			for (Entry<String, String> entry : playersTableColumns.entrySet())
				if (!playersTable.columnExists(entry.getKey()))
					playersTable.addColumn(entry.getKey(), entry.getValue());
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public static void saveConfig(Map<String, Object> map) {
		FileConfiguration config = MCDawn.thisPlugin.getConfig();
		for (final Entry<String, Object> e : map.entrySet())
			if (!config.contains(e.getKey()))
				config.set(e.getKey(), e.getValue());
		MCDawn.thisPlugin.saveConfig();
	}
	
	public static void fileWriteAllLines(String path, List<String> contents) throws IOException {
		File f = new File(path);
		if (!f.exists()) f.createNewFile();
		FileOutputStream out = new FileOutputStream(path);
		out.write(org.apache.commons.lang.StringUtils.join(contents, System.getProperty("line.separator")).getBytes());
		out.flush();
		out.close();
	}
	
	public static <T> List<T> removeDuplicates(List<T> collection) {
		List<T> list = new ArrayList<T>();
		for (T item : collection)
			if (!list.contains(item))
				list.add(item);
		return list;
	}
	
	public static int randomInt(int min, int max) { return min + (int)(Math.random() * ((max - min) + 1)); }
	
	public static String randomNick() { return "MC" + randomInt(1000, 9999); }
	
	public static void broadcastDevs(String message) {
		for (Player p : Bukkit.getServer().getOnlinePlayers())
			if (new PlayerInfo(p).isDev())
				p.sendMessage(message);
	}
	
	public static void broadcastConsoleAndDevs(String message) {
		MCDawn.logger.info(message);
		broadcastDevs(message);
	}
	
	public static Player getPlayerFromString(String playername) {
	    for (Player p : Bukkit.getServer().getOnlinePlayers())
	    	if (p.getName().equalsIgnoreCase(playername) || p.getName().toLowerCase().contains(playername.toLowerCase()))
	    		return p;
	    return null;
    }
}
