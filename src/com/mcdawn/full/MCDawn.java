package com.mcdawn.full;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.*;

import com.mcdawn.full.irc.*;

public class MCDawn extends JavaPlugin implements Listener {
	public static MCDawn thisPlugin;
	
	public static String getVersion() { return "1.0.0"; }
	public static String getExtraVersion() { return "alpha"; }
	
	public static Logger logger;
	
	public static IRC irc;
	public static GlobalChat globalChat;
	
	@Override
	public void onEnable() {
		thisPlugin = this;
		logger = getLogger();
		logger.info("Enabled MCDawn, v" + getVersion() + (getExtraVersion() == "" ? "" : "-" + getExtraVersion() + "."));
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		Util.setupConfig();
		Util.setupCommands();
		Util.setupDatabase();
		FileConfiguration config = getConfig();
		if (config.getBoolean("irc.useIRC")) irc = new IRC();
		if (config.getBoolean("globalchat.useGlobalChat")) globalChat = new GlobalChat();
	}
	
	@Override
	public void onDisable() {
		if (irc != null && irc.isConnected()) irc.shutdown();
		if (globalChat != null && globalChat.isConnected()) globalChat.shutdown();
		logger.info("Disabled MCDawn.");
	}
}
