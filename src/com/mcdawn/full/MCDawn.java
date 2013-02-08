package com.mcdawn.full;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MCDawn extends JavaPlugin {
	public static String getVersion() { return "1.0.0"; }
	public static String getExtraVersion() { return "alpha"; }
	
	public static Logger logger;
	
	@Override
	public void onEnable() {
		try {
			logger = getLogger();
			logger.info("Enabled MCDawn, v" + getVersion() + (getExtraVersion() == "" ? "" : "-" + getExtraVersion() + "."));
			
			// initialize commands
			// TODO: Fix this so it's not broken.
			/*for (PluginCommand cmd : CommandList.getCommands()) {
				getCommand(cmd.getName()).setExecutor(cmd);
				for (String alias : cmd.getAliases())
					getCommand(alias).setExecutor(cmd);
			}*/
		} catch (Exception ex) { ex.printStackTrace(); }
	}
	
	@Override
	public void onDisable() {
		logger.info("Disabled MCDawn.");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("test")) {
			
			return true;
		}
		
		return false;
	}
}
